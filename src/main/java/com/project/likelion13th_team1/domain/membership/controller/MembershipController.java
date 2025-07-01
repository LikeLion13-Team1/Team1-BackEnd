package com.project.likelion13th_team1.domain.membership.controller;

import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;
import com.project.likelion13th_team1.domain.membership.service.command.MembershipCommandService;
import com.project.likelion13th_team1.domain.membership.service.query.MembershipQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Membership", description = "멤버십 관련 API")
public class MembershipController {

    private final MembershipQueryService membershipQueryService;
    private final MembershipCommandService membershipCommandService;

    @Operation(summary = "멤버십 조회")
    @GetMapping("/api/v1/membership")
    public CustomResponse<MembershipResponseDto.MembershipGetResponseDto> getMembership(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return CustomResponse.onSuccess(membershipQueryService.getMembership(customUserDetails.getUsername()));
    }

    @Operation(summary = "멤버십 구독")
    @PostMapping("/api/v1/membership/join")
    public CustomResponse<MembershipResponseDto.MembershipJoinResponseDto> joinMembership(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return CustomResponse.onSuccess(membershipCommandService.joinMembership(customUserDetails.getUsername()));
    }
}
