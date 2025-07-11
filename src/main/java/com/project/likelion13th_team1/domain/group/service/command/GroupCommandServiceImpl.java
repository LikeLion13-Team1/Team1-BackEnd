package com.project.likelion13th_team1.domain.group.service.command;

import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.feature.exception.FeatureErrorCode;
import com.project.likelion13th_team1.domain.feature.exception.FeatureException;
import com.project.likelion13th_team1.domain.feature.repository.FeatureRepository;
import com.project.likelion13th_team1.domain.group.converter.GroupConverter;
import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.group.exception.GroupErrorCode;
import com.project.likelion13th_team1.domain.group.exception.GroupException;
import com.project.likelion13th_team1.domain.group.repository.GroupRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.entity.ExampleRoutine;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.repository.ExampleRoutineRepository;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import com.project.likelion13th_team1.domain.routine.service.command.RoutineCommandService;
import com.project.likelion13th_team1.domain.routine.service.command.RoutineCommandServiceImpl;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupCommandServiceImpl implements GroupCommandService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;
    private final GroupRepository groupRepository;
    private final RoutineCommandService routineCommandService;
    private final ExampleRoutineRepository exampleRoutineRepository;
    private final FeatureRepository featureRepository;

    @Override
    public GroupResponseDto.GroupCreateResponseDto createGroup(String email, GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Long groupCount = groupRepository.countByMember(member);

        if (groupCount == 3) {
            throw new GroupException(GroupErrorCode.GROUP_LIMIT_EXCEEDED);
        }

        Group group = GroupConverter.toGroup(groupCreateRequestDto, member);

        groupRepository.save(group);

        // ex routine 에서 루틴 기본 세팅 가져오기
        List<ExampleRoutine> exampleRoutines = exampleRoutineRepository.findAll();

        for (ExampleRoutine exampleRoutine : exampleRoutines) {
            routineCommandService.createExampleRoutine(exampleRoutine, group);
        }

        return GroupConverter.toGroupCreateResponseDto(group);
    }

    @Override
    public void updateGroup(String email, Long groupId, GroupRequestDto.GroupUpdateRequestDto groupUpdateRequestDto) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        group.update(groupUpdateRequestDto);
    }

    @Override
    public void deleteGroup(String email, Long groupId) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        if (!group.getMember().getEmail().equals(member.getEmail())) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        // TODO : 고아가 된 루틴 삭제처리

        List<Routine> routinesToDelete = routineRepository.findByGroup(group);
        routineRepository.deleteAll(routinesToDelete);
        groupRepository.flush();
    }

    @Override
    public void createRecommendedRoutineGroup(String email, Long groupId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Feature feature = featureRepository.findByMember(member)
                .orElseThrow(() -> new FeatureException(FeatureErrorCode.FEATURE_NOT_FOUND));

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        // 그룹에 이전에 있던 내용 초기화
        List<Routine> routinesToDelete = routineRepository.findByGroup(group);
        routineRepository.deleteAll(routinesToDelete);
        groupRepository.flush();

        // 특성 점수 합계로 루틴 세트를 받는다.
        routineCommandService.createRecommendedRoutines(member.getPersonality(), group);
    }

    public void initGroup(Member member){
        // TODO : 매직넘버
        List<String> groupName = List.of("루틴 그룹1", "루틴 그룹2", "루틴 그룹3");

        for (String s : groupName) {
            Group group = Group.builder()
                    .name(s)
                    .member(member)
                    .build();
            groupRepository.save(group);
        }
    }

    private int getRandomInt() {
        Random random = new Random();
        return random.nextInt(6);
    }
}
