package com.project.likelion13th_team1.domain.member.converter;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.SocialLogin;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SocialLoginConverter {

    public static SocialLogin toSocialLogin(MemberRequestDto.MemberSocialCreateRequestDto dto, Member member) {
        return SocialLogin.builder()
                .socialId(dto.socialId())
                .member(member)
                .socialType(dto.socialType())
                .build();
    }
}
