package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.global.feature.dto.response.FeatureResponseDto;

public interface MemberCommandService {
    void createMember(MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto);

    void updateMember(String email, MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto);

    void deleteMember(String email);

    // TODO : 이걸 뭔가 공통으로 묶을 수 있지 않을까?
    void createFeature(String email, FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto);

    void updateFeature(String email, FeatureRequestDto.FeatureUpdateRequestDto featureUpdateRequestDto);

    FeatureResponseDto.FeatureDetailResponseDto getFeature(String email);
}
