package com.project.likelion13th_team1.domain.member.dto.request;

import com.project.likelion13th_team1.global.security.utils.PasswordPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

public class MemberRequestDto {

    @Builder
    public record MemberCreateRequestDto(
            @NotBlank String username,
            @NotBlank @Email String email,
            @NotBlank
            @Pattern(regexp = PasswordPattern.REGEXP, message = PasswordPattern.MESSAGE)
            String password,
            String profileImage
    ) {
    }

    public record MemberUpdateRequestDto(
            String username,
            String profileImage
            //TODO : 유저 특성도 이걸로 바꿀건지?
    ){
    }
}
