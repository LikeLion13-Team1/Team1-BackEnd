package com.project.likelion13th_team1.global.security.auth.dto.request;

import com.project.likelion13th_team1.global.security.utils.PasswordPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public class AuthRequestDto {

    @Builder
    public record LoginRequestDto(
            String email,
            String password
    ) {
    }

    public record PasswordResetRequestDto(
            @NotBlank
            String currentPassword,
            @NotBlank
            @Pattern(regexp = PasswordPattern.REGEXP, message = PasswordPattern.MESSAGE)
            String newPassword,
            @NotBlank
            @Pattern(regexp = PasswordPattern.REGEXP, message = PasswordPattern.MESSAGE)
            String newPasswordConfirmation
    ) {
    }
}
