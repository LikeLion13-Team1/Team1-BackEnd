package com.project.likelion13th_team1.domain.member.converter;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Role;
import com.project.likelion13th_team1.domain.member.entity.SocialType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberConverter {

    public static Member toMember(MemberRequestDto.MemberCreateRequestDto dto) {
        return Member.builder()
                .username(dto.username())
                .email(dto.email())
//                .password(password)
                .role(Role.USER)
                .socialType(SocialType.LOCAL)
                .build();
    }

    public static MemberResponseDto.MemberDetailResponseDto toMemberDetailResponseDto(Member member) {
        return MemberResponseDto.MemberDetailResponseDto.builder()
                .username(member.getUsername())
                .email(member.getEmail())
                .profileImage(member.getProfileImage())
                .socialType(member.getSocialType())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
