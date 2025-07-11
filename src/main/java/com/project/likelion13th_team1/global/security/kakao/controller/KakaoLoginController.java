package com.project.likelion13th_team1.global.security.kakao.controller;

import com.project.likelion13th_team1.domain.member.converter.MemberConverter;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Role;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.member.service.command.MemberCommandService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.jwt.JwtUtil;
import com.project.likelion13th_team1.global.security.jwt.dto.JwtDto;
import com.project.likelion13th_team1.global.security.kakao.dto.response.KakaoUserInfoResponseDto;
import com.project.likelion13th_team1.global.security.kakao.service.KakaoService;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Tag(name = "Kakao", description = "카카오 로그인 컨트롤러")
public class KakaoLoginController {

    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;
    private final MemberCommandService memberCommandService;
    private final JwtUtil jwtUtil;

    @GetMapping("/callback/kakao")
    public CustomResponse<String> callback(
            @RequestParam("code") String code,
            HttpServletResponse response

    ) throws IOException {

        // 1. 카카오 인증서버에서 토큰을 발급받는다.
        // 인가code와 Redirect URL을 파라미터로 전달하여 카카오 인증서버에 요청.
        String accessToken = kakaoService.getAccessTokenFromKakao(code);


        // 2. 1번에서 받은 토큰으로 카카오 리소스 서버에 사용자 정보 요청.
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);

        // 3. 회원가입 & 로그인 처리
        // 여기에 서버 사용자 로그인(인증) 또는 회원가입 로직 추가
        log.info("email = {}", userInfo.kakaoAccount().email());
        Optional<Member> member = memberRepository.findByEmailAndNotDeleted(userInfo.kakaoAccount().email());




        if (member.isEmpty()) {
            // 존재하지 않으니 회원가입으로 이동
            log.info("[ KakaoLoginController ] 회원이 아닙니다. 회원 가입을 먼저 진행합니다");
            MemberRequestDto.MemberSocialCreateRequestDto memberSocialCreateRequestDto =
                    MemberConverter.toMemberSocialCreateRequestDto(userInfo);

            memberCommandService.createSocialMember(memberSocialCreateRequestDto);
        }
        // 로그인으로 이동
        log.info("[ KakaoLoginController ] 회원 정보가 존재해 로그인을 진행합니다");
        CustomUserDetails customUserDetails = new CustomUserDetails(userInfo.kakaoAccount().email(), null, Role.USER);

//        //Client 에게 줄 Response 를 Build
//        JwtDto jwtDto = JwtDto.builder()
//                .accessToken(jwtUtil.createJwtAccessToken(customUserDetails)) //access token 생성
//                .refreshToken(jwtUtil.createJwtRefreshToken(customUserDetails)) //refresh token 생성
//                .build();
//
        String jwtAccessToken = jwtUtil.createJwtAccessToken(customUserDetails);
        String jwtRefreshToken = jwtUtil.createJwtRefreshToken(customUserDetails);


        log.info("[KakaoLoginController ] access = {}, refresh = {}", jwtAccessToken, jwtRefreshToken);

        // ✅ 프론트엔드로 리다이렉트
        String frontendRedirectUrl = "http://127.0.0.1:5500/Team1-FrontEnd/pages/home2.html"
                + "?accessToken=" + jwtAccessToken
                + "&refreshToken=" + jwtRefreshToken;

        log.info("frontendRedirectUrl {}", frontendRedirectUrl);
        response.sendRedirect(frontendRedirectUrl);

        // CustomResponse 사용하여 응답 통일
        return CustomResponse.onSuccess("카카오 리다이렉트");
    }
}