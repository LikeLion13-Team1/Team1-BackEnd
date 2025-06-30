package com.project.likelion13th_team1.domain.member.controller;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.service.command.MemberCommandService;
import com.project.likelion13th_team1.domain.member.service.query.MemberQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
@Tag(name = "Member", description = "멤버 관련 API")
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public CustomResponse<String> createMember(
            @RequestBody MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto
    ) {
        memberCommandService.createMember(memberCreateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 가입 성공");
    }

    @Operation(summary = "회원 정보 수정")
    @PatchMapping("/me")
    public CustomResponse<String> updateMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        memberCommandService.updateMember(customUserDetails.getUsername(), memberUpdateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.OK, "회원 정보 수정 완료");
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping("/info")
    public CustomResponse<MemberResponseDto.MemberDetailResponseDto> getMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return CustomResponse.onSuccess(memberQueryService.getMember(customUserDetails.getUsername()));
    }

    // TODO : 스케쥴러 구현하기
    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/withdrawal")
    public CustomResponse<String> deleteMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        memberCommandService.deleteMember(customUserDetails.getUsername());
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "회원 탈퇴 완료");
    }

    @Operation(summary = "회원 특성 정보 생성")
    @PostMapping("/feature")
    public CustomResponse<String> createFeature(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto
            ) {
        memberCommandService.createFeature(customUserDetails.getUsername(), featureCreateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 특성 정보 생성 완료");
    }
}
