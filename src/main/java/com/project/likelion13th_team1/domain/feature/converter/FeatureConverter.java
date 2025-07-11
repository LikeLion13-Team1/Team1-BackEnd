package com.project.likelion13th_team1.domain.feature.converter;

import com.project.likelion13th_team1.domain.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.domain.feature.dto.response.FeatureResponseDto;
import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Personality;

public class FeatureConverter {

    public static Feature toFeature(FeatureRequestDto.FeatureCreateRequestDto dto, Member member) {
        return Feature.builder()
                .q1(dto.q1())
                .q2(dto.q2())
                .q3(dto.q3())
                .q4(dto.q4())
                .member(member)
                .build();
    }

    public static FeatureResponseDto.FeatureDetailResponseDto toFeatureDetailResponseDto(Feature feature, Personality personality) {
        return FeatureResponseDto.FeatureDetailResponseDto.builder()
                .featureId(feature.getId())
                .q1(feature.getQ1())
                .q2(feature.getQ2())
                .q3(feature.getQ3())
                .q4(feature.getQ4())
                .total(getTotal(feature))
                .personality(personality)
                .build();
    }

    private static Integer getTotal(Feature feature) {
        return feature.getQ1() + feature.getQ2() + feature.getQ3() + feature.getQ4();
    }
}
