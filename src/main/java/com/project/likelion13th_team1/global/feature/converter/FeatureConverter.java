package com.project.likelion13th_team1.global.feature.converter;

import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.global.feature.dto.response.FeatureResponseDto;
import com.project.likelion13th_team1.global.feature.entity.Feature;
import com.project.likelion13th_team1.global.feature.entity.FeatureType;

public class FeatureConverter {

    public static Feature toFeature(FeatureRequestDto.FeatureCreateRequestDto dto, FeatureType featureType) {
        return Feature.builder()
                .messyHouse(dto.messyHouse())
                .cleaningStyle(dto.cleaningStyle())
                .cleanHouse(dto.cleanHouse())
                .routineStyle(dto.routineStyle())
                .featureType(featureType)
                .build();
    }

    public static FeatureResponseDto.FeatureDetailResponseDto toFeatureDetailResponseDto(Feature feature) {
        return FeatureResponseDto.FeatureDetailResponseDto.builder()
                .featureId(feature.getId())
                .messyHouse(feature.getMessyHouse())
                .cleaningStyle(feature.getCleaningStyle())
                .cleanHouse(feature.getCleanHouse())
                .routineStyle(feature.getRoutineStyle())
                .build();
    }
}
