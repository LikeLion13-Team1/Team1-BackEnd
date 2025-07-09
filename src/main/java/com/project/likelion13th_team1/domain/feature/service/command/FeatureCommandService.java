package com.project.likelion13th_team1.domain.feature.service.command;

import com.project.likelion13th_team1.domain.feature.dto.request.FeatureRequestDto;

public interface FeatureCommandService {

    // TODO : 이걸 뭔가 공통으로 묶을 수 있지 않을까?
    void createFeature(String email, FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto);

    void updateFeature(String email, FeatureRequestDto.FeatureUpdateRequestDto featureUpdateRequestDto);


}
