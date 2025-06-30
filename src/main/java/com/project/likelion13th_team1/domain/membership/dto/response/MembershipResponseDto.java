package com.project.likelion13th_team1.domain.membership.dto.response;

import com.project.likelion13th_team1.domain.membership.entity.MembershipStatus;
import lombok.Builder;

import java.time.LocalDateTime;

public class MembershipResponseDto {

    @Builder
    public record MembershipGetResponseDto(
            Long id,
            MembershipStatus membershipStatus,
            LocalDateTime joinedAt,
            LocalDateTime expiredAt
    ) {
    }
}
