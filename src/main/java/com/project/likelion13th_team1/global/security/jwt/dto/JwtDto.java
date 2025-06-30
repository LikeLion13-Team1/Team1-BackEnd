package com.project.likelion13th_team1.global.security.jwt.dto;

import lombok.Builder;

@Builder
public record JwtDto(
    String accessToken,
    String refreshToken
) {
}
