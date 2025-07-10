package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Personality;

public interface MemberCommandService {
    void createLocalMember(MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto);

    void createSocialMember(MemberRequestDto.MemberSocialCreateRequestDto memberSocialCreateRequestDto);

    void updateMember(String email, MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto);

    void deleteMember(String email);

    void updatePersonality(Member member, Personality personality);
}
