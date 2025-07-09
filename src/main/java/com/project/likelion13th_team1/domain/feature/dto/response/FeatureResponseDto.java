package com.project.likelion13th_team1.domain.feature.dto.response;

import lombok.Builder;

public class FeatureResponseDto {

    @Builder
    public record FeatureDetailResponseDto(
            Long featureId,
            Integer q1,
            Integer q2,
            Integer q3,
            Integer q4
    ) {
    }
}
