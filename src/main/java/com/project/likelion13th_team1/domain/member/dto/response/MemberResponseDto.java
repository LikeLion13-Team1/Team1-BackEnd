package com.project.likelion13th_team1.domain.member.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResponseDto {

    @Builder
    public record MemberCreateResponseDto(
            Long id,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record MemberUpdateResponseDto(
            LocalDateTime updatedAt
    ) {
    }
}
