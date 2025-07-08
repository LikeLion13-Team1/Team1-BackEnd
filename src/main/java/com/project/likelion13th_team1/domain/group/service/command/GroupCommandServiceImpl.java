package com.project.likelion13th_team1.domain.group.service.command;

import com.project.likelion13th_team1.domain.group.converter.GroupConverter;
import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.group.repository.GroupRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupCommandServiceImpl implements GroupCommandService {

    private final MemberRepository memberRepository;
    private final GroupRepository groupRepository;

    @Override
    public GroupResponseDto.GroupCreateResponseDto createGroup(String email, GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Group group = GroupConverter.toGroup(groupCreateRequestDto, member);

        groupRepository.save(group);

        return GroupConverter.toGroupCreateResponseDto(group);
    }
}
