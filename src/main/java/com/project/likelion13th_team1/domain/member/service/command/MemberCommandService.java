package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;

public interface MemberCommandService {
    MemberResponseDto.MemberCreateResponseDto createMember(MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto);

    MemberResponseDto.MemberUpdateResponseDto updateMember(String email, MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto);

    void deleteMember(String email);
}
