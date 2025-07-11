package com.project.likelion13th_team1.global.security.auth.dto.response;

import lombok.Builder;

public class AuthResponseDto {

    @Builder
    public record PasswordTokenResponseDto(
            String uuid
    ){
    }
}
