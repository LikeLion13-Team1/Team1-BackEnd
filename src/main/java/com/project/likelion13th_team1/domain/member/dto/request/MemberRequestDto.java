package com.project.likelion13th_team1.domain.member.dto.request;

import lombok.Builder;

public class MemberRequestDto {

    @Builder
    public record MemberCreateRequestDto(
            String username,
            String email,
            String password
    ) {
    }
}
