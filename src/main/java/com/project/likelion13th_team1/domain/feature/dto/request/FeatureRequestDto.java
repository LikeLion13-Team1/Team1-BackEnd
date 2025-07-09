package com.project.likelion13th_team1.domain.feature.dto.request;

public class FeatureRequestDto {

    public record FeatureCreateRequestDto(
            Integer q1,
            Integer q2,
            Integer q3,
            Integer q4
    ) {
    }

    public record FeatureUpdateRequestDto(
            Integer q1,
            Integer q2,
            Integer q3,
            Integer q4
    ) {
    }
}
