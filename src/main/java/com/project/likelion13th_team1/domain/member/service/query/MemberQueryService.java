package com.project.likelion13th_team1.domain.member.service.query;

import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;

public interface MemberQueryService {

    MemberResponseDto.MemberDetailResponseDto getMember(String email);
}
