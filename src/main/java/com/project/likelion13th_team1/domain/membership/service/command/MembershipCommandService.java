package com.project.likelion13th_team1.domain.membership.service.command;

import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;

public interface MembershipCommandService {
    MembershipResponseDto.MembershipJoinResponseDto joinMembership(String email);
    MembershipResponseDto.MembershipWithdrawResponseDto withdrawMembership(String email);
}
