package com.project.likelion13th_team1.global.security.auth.controller;

import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.auth.dto.request.AuthRequestDto;
import com.project.likelion13th_team1.global.security.auth.service.AuthService;
import com.project.likelion13th_team1.global.security.jwt.dto.JwtDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.SignatureException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth 관련 API")
public class AuthController {

    private final AuthService authService;

    //토큰 재발급 API
    @Operation(summary = "토큰 재발급")
    @PostMapping("/reissue")
    public CustomResponse<?> reissue(
            @RequestBody JwtDto jwtDto
    ) throws SignatureException {

        log.info("[ Auth Controller ] 토큰을 재발급합니다. ");

        return CustomResponse.onSuccess(authService.reissueToken(jwtDto));
    }

    @Operation(summary = "login")
    @PostMapping("/login")
    public CustomResponse<?> login(
            @RequestBody AuthRequestDto.LoginRequestDto memberLoginRequestDto
    ) {
        return null;
    }

    @Operation(summary = "logout")
    @PostMapping("/logout")
    public CustomResponse<?> logout() {
        return null;
    }
}
