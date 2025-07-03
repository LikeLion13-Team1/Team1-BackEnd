package com.project.likelion13th_team1.global.feature.dto.request;

import com.project.likelion13th_team1.global.feature.entity.CleanHouse;
import com.project.likelion13th_team1.global.feature.entity.CleaningStyle;
import com.project.likelion13th_team1.global.feature.entity.MessyHouse;
import com.project.likelion13th_team1.global.feature.entity.RoutineStyle;

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
