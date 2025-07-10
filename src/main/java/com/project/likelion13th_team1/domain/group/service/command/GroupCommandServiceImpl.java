package com.project.likelion13th_team1.domain.group.service.command;

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
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import com.project.likelion13th_team1.domain.routine.service.command.RoutineCommandServiceImpl;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupCommandServiceImpl implements GroupCommandService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;
    private final GroupRepository groupRepository;
    private final RoutineCommandServiceImpl routineCommandServiceImpl;

    @Override
    public GroupResponseDto.GroupCreateResponseDto createGroup(String email, GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Group group = GroupConverter.toGroup(groupCreateRequestDto, member);

        groupRepository.save(group);

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

        groupRepository.delete(group);
    }
}
