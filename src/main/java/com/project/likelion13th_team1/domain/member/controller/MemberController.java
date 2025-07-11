package com.project.likelion13th_team1.domain.member.controller;

import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.service.command.MemberCommandService;
import com.project.likelion13th_team1.domain.member.service.query.MemberQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "회원가입", description = "유저이름이 공백이면 안됨<br/>이메일 형식을 지켜야함<br/>비밀번호는 문자, 숫자, 특수문자를 포함한 8자 이상")
    @PostMapping("/signup")
    public CustomResponse<String> createLocalMember(
            @RequestBody MemberRequestDto.MemberCreateRequestDto memberCreateRequestDto
    ) {
        memberCommandService.createLocalMember(memberCreateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 가입 성공");
    }

    @Operation(summary = "회원 정보 수정", description = "모든 정보 수정 요청에는 username이 포함되어야 함")
    @PatchMapping("/me")
    public CustomResponse<String> updateMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid MemberRequestDto.MemberUpdateRequestDto memberUpdateRequestDto
    ) {
        memberCommandService.updateMember(customUserDetails.getUsername(), memberUpdateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.OK, "회원 정보 수정 완료");
    }

    @Operation(summary = "회원 정보 조회", description = "유저 이름, 이메일과 같은 회원 정보를 조회한다")
    @GetMapping("/info")
    public CustomResponse<MemberResponseDto.MemberDetailResponseDto> getMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return CustomResponse.onSuccess(memberQueryService.getMember(customUserDetails.getUsername()));
    }

    // TODO : 스케쥴러 구현하기
    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴, 실제 DB에서 삭제되는 것이 아닌 soft delete")
    @DeleteMapping("/withdrawal")
    public CustomResponse<String> deleteMember(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        memberCommandService.deleteMember(customUserDetails.getUsername());
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "회원 탈퇴 완료");
    }

}
