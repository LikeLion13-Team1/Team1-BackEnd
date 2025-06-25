package com.project.likelion13th_team1.domain.member.controller;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.service.command.MemberCommandService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Member", description = "멤버 관련 API")
public class MemberController {

    private final MemberCommandService memberCommandService;

    @Operation
    @PostMapping("signup")
    public CustomResponse<MemberResponseDto.MemberCreateResponseDto> createMember(
            @RequestBody MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto
    ) {
        return CustomResponse.onSuccess(memberCommandService.createMember(memberCreateRequestDto));
    }
}
