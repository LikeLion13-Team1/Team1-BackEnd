package com.project.likelion13th_team1.domain.member.controller;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.service.command.MemberCommandService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Tag(name = "Member", description = "멤버 관련 API")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    public CustomResponse<MemberResponseDto.MemberCreateResponseDto> createMember(
            @RequestBody MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto
    ) {
        return CustomResponse.onSuccess(memberCommandService.createMember(memberCreateRequestDto));
    }

    @Operation(summary = "회원정보 수정")
    @PatchMapping("/me")
    public CustomResponse<MemberResponseDto.MemberUpdateResponseDto> updateMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        return CustomResponse.onSuccess(memberCommandService.updateMember(customUserDetails.getUsername(), memberUpdateRequestDto));
    }
}
