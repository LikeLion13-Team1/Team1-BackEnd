package com.project.likelion13th_team1.domain.group.service.query;

import com.project.likelion13th_team1.domain.group.converter.GroupConverter;
import com.project.likelion13th_team1.domain.group.dto.GroupDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.group.exception.GroupErrorCode;
import com.project.likelion13th_team1.domain.group.exception.GroupException;
import com.project.likelion13th_team1.domain.group.repository.GroupRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupQueryServiceImpl implements GroupQueryService {

    private final GroupRepository groupRepository;
    private final MemberRepository memberRepository;

    @Override
    public GroupResponseDto.GroupDetailResponseDto getGroup(String email, Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GroupException(GroupErrorCode.GROUP_NOT_FOUND));

        if (!group.getMember().getEmail().equals(email)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        return GroupConverter.toGroupDetailResponseDto(group);
    }

    @Override
    public GroupResponseDto.GroupCursorResponseDto getGroupCursor(String email, Long cursor, Integer size) {

        Pageable pageable = PageRequest.of(0, size);

        // cursor가 0일 경우(첫페이지) cursor 최대값
        if (cursor == 0) {
            cursor = Long.MIN_VALUE;
        }

        Slice<GroupDto> groupDtosSlice
                = groupRepository.findAllByGroupIdGreaterThanOrderByGroupIdASC(email, cursor, pageable);

        return GroupConverter.toGroupCursorResponseDto(groupDtosSlice);
    }
}
