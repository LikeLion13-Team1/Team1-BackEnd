package com.project.likelion13th_team1.domain.member.dto.response;

import com.project.likelion13th_team1.domain.member.entity.SocialType;
import lombok.Builder;

import java.time.LocalDateTime;

public class MemberResponseDto {

    @Builder
    public record MemberDetailResponseDto(
            String username,
            String email,
            String profileImage,
            SocialType socialType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
