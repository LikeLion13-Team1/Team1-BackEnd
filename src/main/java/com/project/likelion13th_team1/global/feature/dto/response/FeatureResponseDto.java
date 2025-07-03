package com.project.likelion13th_team1.global.feature.dto.response;

import com.project.likelion13th_team1.global.feature.entity.CleanHouse;
import com.project.likelion13th_team1.global.feature.entity.CleaningStyle;
import com.project.likelion13th_team1.global.feature.entity.MessyHouse;
import com.project.likelion13th_team1.global.feature.entity.RoutineStyle;
import lombok.Builder;

public class FeatureResponseDto {

    @Builder
    public record FeatureDetailResponseDto(
            Long featureId,
            MessyHouse messyHouse,
            CleaningStyle cleaningStyle,
            CleanHouse cleanHouse,
            RoutineStyle routineStyle
    ) {
    }
}
