package com.project.likelion13th_team1.global.security.auth.dto.request;

import lombok.Builder;

public class AuthRequestDto {

    @Builder
    public record LoginRequestDto(
            String email,
            String password
    ) {
    }
}
