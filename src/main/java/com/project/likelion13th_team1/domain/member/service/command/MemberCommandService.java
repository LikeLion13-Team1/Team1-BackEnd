package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;

public interface MemberCommandService {
    void createLocalMember(MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto);

    void updateMember(String email, MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto);

    void deleteMember(String email);

}
