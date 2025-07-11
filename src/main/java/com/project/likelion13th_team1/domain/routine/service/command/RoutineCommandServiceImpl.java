package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.event.service.command.EventCommandService;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.group.exception.GroupErrorCode;
import com.project.likelion13th_team1.domain.group.exception.GroupException;
import com.project.likelion13th_team1.domain.group.repository.GroupRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Personality;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.ExampleRoutine;
import com.project.likelion13th_team1.domain.routine.entity.RecommendedRoutine;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RecommendedRoutineRepository;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineCommandServiceImpl implements RoutineCommandService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;
    private final EventCommandService eventCommandService;
    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;
    private final RecommendedRoutineRepository recommendedRoutineRepository;

    @Override
    public RoutineResponseDto.RoutineCreateResponseDto createRoutine(Long groupId, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        LocalDate startAt = routineCreateRequestDto.startAt();
        LocalDate endAt = routineCreateRequestDto.endAt();

        if (endAt != null && startAt.isAfter(endAt)) {
            throw new RoutineException(RoutineErrorCode.ROUTINE_END_AT_IS_EARLIER_THAN_START_AT);
        }

        Routine routine = RoutineConverter.toRoutine(routineCreateRequestDto, group);
        routineRepository.save(routine);

        int eventCount = eventCommandService.createEvent(routine);

        return RoutineConverter.toRoutineCreateResponseDto(routine, eventCount);
    }

    @Override
    public RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto) {
        // TODO : update랑 delete 공통 부분을 private method로 묶기?
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }
        LocalDate startAt = routineUpdateRequestDto.startAt();
        LocalDate endAt = routineUpdateRequestDto.endAt();

        if (endAt != null && startAt.isAfter(endAt)) {
            throw new RoutineException(RoutineErrorCode.ROUTINE_END_AT_IS_EARLIER_THAN_START_AT);
        }

        routine.updateRoutine(routineUpdateRequestDto);
        eventRepository.deleteByRoutine(routine);
        eventCommandService.createEvent(routine);
        return RoutineConverter.toRoutineUpdateResponseDto(routine);
    }

    @Override
    public void createExampleRoutine(ExampleRoutine exampleRoutine, Group group) {
        Routine routine = RoutineConverter.toRoutine(exampleRoutine, group);
        routineRepository.save(routine);
//        eventCommandService.createEvent(routine);
    }

    // TODO : 스케줄러 구현하기
    @Override
    public void deleteRoutine(String email, Long routineId) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

//        eventCommandService.deleteOrphanedEvent(routine);
        routineRepository.delete(routine);
    }

    @Override
    public void activateRoutine(String email, Long routineId) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        routine.activate();
        eventCommandService.createEvent(routine);

    }

    @Override
    public void inactivateRoutine(String email, Long routineId) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        routine.inactivate();
        eventRepository.deleteByRoutine(routine);
    }

    public void createRecommendedRoutines(Personality personality, Group group) {

        List<RecommendedRoutine> recommendedRoutines = recommendedRoutineRepository.findByPersonality(personality);

        Collections.shuffle(recommendedRoutines);

        List<RecommendedRoutine> selectedRoutines = recommendedRoutines.stream()
                .limit(20)
                .toList();

        List<Routine> routinesToSave = selectedRoutines.stream()
                .map(recommendedRoutine -> RoutineConverter.toRoutine(recommendedRoutine, group))
                .toList();

        routineRepository.saveAll(routinesToSave);
    }

//    private int getRandom(){
//        Random random = new Random();
//        return random.nextInt(2);
//    }
}
