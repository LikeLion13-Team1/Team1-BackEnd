package com.project.likelion13th_team1.domain.membership.converter;

import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;
import com.project.likelion13th_team1.domain.membership.entity.Membership;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MembershipConverter {

    public MembershipResponseDto.MembershipGetResponseDto toMembershipGetResponseDto(Membership membership) {
        return MembershipResponseDto.MembershipGetResponseDto.builder()
                .id(membership.getId())
                .membershipStatus(membership.getStatus())
                .joinedAt(membership.getJoinedAt())
                .expiredAt(membership.getExpiredAt())
                .build();
    }
}
