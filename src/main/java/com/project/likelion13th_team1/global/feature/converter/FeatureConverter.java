package com.project.likelion13th_team1.global.feature.converter;

import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
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
}
