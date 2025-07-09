package com.project.likelion13th_team1.domain.feature.service.query;

import com.project.likelion13th_team1.domain.feature.dto.response.FeatureResponseDto;

public interface FeatureQueryService {

    FeatureResponseDto.FeatureDetailResponseDto getFeature(String email);
}
