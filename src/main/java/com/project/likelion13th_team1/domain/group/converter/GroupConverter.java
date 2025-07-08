package com.project.likelion13th_team1.domain.group.converter;

import com.project.likelion13th_team1.domain.group.dto.GroupDto;
import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

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

    public static GroupResponseDto.GroupCursorResponseDto toGroupCursorResponseDto(Slice<GroupDto> groupDtosSlice) {
        List<GroupResponseDto.GroupDetailResponseDto> groupList = groupDtosSlice.stream()
                .map(groupDto -> toGroupDetailResponseDto(groupDto.group()))
                .toList();

        // 다음 cursor 지정
        Long nextCursor = null;
        if (!groupDtosSlice.isEmpty() && groupDtosSlice.hasNext()) {
            nextCursor = groupDtosSlice.getContent().get(groupDtosSlice.getNumberOfElements() - 1).group().getId();
        }

        return GroupResponseDto.GroupCursorResponseDto.builder()
                .groups(groupList)
                .hasNextCursor(groupDtosSlice.hasNext())
                .nextCursor(nextCursor)
                .build();

    }
}
