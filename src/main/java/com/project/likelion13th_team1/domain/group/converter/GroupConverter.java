package com.project.likelion13th_team1.domain.group.converter;

import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.member.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GroupConverter {

    public static Group toGroup(GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto, Member member) {
        return Group.builder()
                .name(groupCreateRequestDto.name())
                .member(member)
                .build();
    }

    public static GroupResponseDto.GroupCreateResponseDto toGroupCreateResponseDto(Group group) {
        return GroupResponseDto.GroupCreateResponseDto.builder()
                .groupId(group.getId())
                .build();
    }

    public static GroupResponseDto.GroupDetailResponseDto toGroupDetailResponseDto(Group group) {
        return GroupResponseDto.GroupDetailResponseDto.builder()
                .groupId(group.getId())
                .name(group.getName())
                .build();
    }
}
