package com.project.likelion13th_team1.global.feature.dto.response;

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
