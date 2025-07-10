package com.project.likelion13th_team1.domain.group.dto.response;

import lombok.Builder;

import java.util.List;

public class GroupResponseDto {

    @Builder
    public record GroupCreateResponseDto(
            Long groupId
    ) {
    }

    @Builder
    public record GroupDetailResponseDto(
            Long groupId,
            String name
    ) {
    }

    @Builder
    public record GroupCursorResponseDto(
            List<GroupDetailResponseDto> groups,
            Long nextCursor,
            Boolean hasNextCursor
    ) {
    }

}
