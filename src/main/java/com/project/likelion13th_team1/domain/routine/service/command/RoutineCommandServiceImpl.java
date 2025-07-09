package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.event.service.command.EventCommandService;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.group.exception.GroupErrorCode;
import com.project.likelion13th_team1.domain.group.exception.GroupException;
import com.project.likelion13th_team1.domain.group.repository.GroupRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineCommandServiceImpl implements RoutineCommandService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;
    private final EventCommandService eventCommandService;
    private final GroupRepository groupRepository;
    private final EventRepository eventRepository;

    @Override
    public RoutineResponseDto.RoutineCreateResponseDto createRoutine(Long groupId, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto) {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        Routine routine = RoutineConverter.toRoutine(routineCreateRequestDto, group);
        routineRepository.save(routine);

        int eventCount = eventCommandService.createEvent(routine);

        return RoutineConverter.toRoutineCreateResponseDto(routine, eventCount);
    }

    @Override
    public RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        if (routineUpdateRequestDto.name() != null) routine.updateName(routineUpdateRequestDto.name());
        if (routineUpdateRequestDto.description() != null) routine.updateDescription(routineUpdateRequestDto.description());

        if (routineUpdateRequestDto.repeatDays() != null) {
            routine.getRepeatDays().clear();
            routine.getRepeatDays().addAll(routineUpdateRequestDto.repeatDays());
        }
        return RoutineConverter.toRoutineUpdateResponseDto(routine);
    }

    // TODO : 스케줄러 구현하기
    @Override
    public void deleteRoutine(String email, Long routineId) {
        Member member = memberRepository.findByEmail(email)
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
        Member member = memberRepository.findByEmail(email)
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
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getGroup().getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        routine.inactivate();
        // 비활성화 된 루틴에서 생성된 이벤트 제거
        eventRepository.deleteByRoutine(routine);
    }
}
