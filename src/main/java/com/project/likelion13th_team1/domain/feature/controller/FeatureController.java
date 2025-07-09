package com.project.likelion13th_team1.domain.feature.controller;

import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.domain.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.domain.feature.dto.response.FeatureResponseDto;
import com.project.likelion13th_team1.domain.feature.service.command.FeatureCommandService;
import com.project.likelion13th_team1.domain.feature.service.query.FeatureQueryService;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/features")
@Tag(name = "Feature", description = "특성 이벤트 관련 API")
public class FeatureController {

    private final FeatureCommandService featureCommandService;
    private final FeatureQueryService featureQueryService;

    @Operation(summary = "회원 특성 정보 생성", description = "루틴 추천을 위한 회원 특성 정보를 생성한다.")
    @PostMapping("/feature")
    public CustomResponse<String> createFeature(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto
    ) {
        featureCommandService.createFeature(customUserDetails.getUsername(), featureCreateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 특성 정보 생성 완료");
    }

    @Operation(summary = "회원 특성 정보 수정", description = "루틴 추천을 위한 회원 특성 정보를 수정한다.")
    @PatchMapping("/feature")
    public CustomResponse<String> updateFeature(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FeatureRequestDto.FeatureUpdateRequestDto featureUpdateRequestDto
    ) {
        featureCommandService.updateFeature(customUserDetails.getUsername(), featureUpdateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.OK, "회원 특성 정보 수정 완료");
    }

    @Operation(summary = "회원 특성 정보 조회", description = "회원 특성 정보를 조회한다.")
    @GetMapping("/feature")
    public CustomResponse<FeatureResponseDto.FeatureDetailResponseDto> getFeature(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return CustomResponse.onSuccess(featureQueryService.getFeature(customUserDetails.getUsername()));
    }
}
