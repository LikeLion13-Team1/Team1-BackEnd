package com.project.likelion13th_team1.global.feature.dto.request;

public class FeatureRequestDto {

    public record FeatureCreateRequestDto(
            MessyHouse messyHouse,
            CleaningStyle cleaningStyle,
            CleanHouse cleanHouse,
            RoutineStyle routineStyle
    ) {
    }

    public record FeatureUpdateRequestDto(
            MessyHouse messyHouse,
            CleaningStyle cleaningStyle,
            CleanHouse cleanHouse,
            RoutineStyle routineStyle
    ) {
    }
}
