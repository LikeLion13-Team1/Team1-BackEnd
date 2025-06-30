package com.project.likelion13th_team1.domain.membership.service.query;

import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;

public interface MembershipQueryService {
    MembershipResponseDto.MembershipGetResponseDto getMembership(String email);
}
