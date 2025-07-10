package com.project.likelion13th_team1.global.security.kakao.service;

import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.security.jwt.JwtUtil;
import com.project.likelion13th_team1.global.security.kakao.dto.response.KakaoTokenResponseDto;
import com.project.likelion13th_team1.global.security.kakao.dto.response.KakaoUserInfoResponseDto;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KakaoService {


    private final String clientId;     // API Key
    private final String tokenURI;    // 카카오 인증 서버
    private final String userInfoURI;     // 카카오 리소스 서버
    private final String redirectURI;  // redirect URI
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    @Autowired
    public KakaoService(@Value("${spring.security.oauth2.client.registration.kakao.client-id}") String clientId,
                        @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}") String redirectURI,
                        @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}") String userInfoURI,
                        @Value("${spring.security.oauth2.client.provider.kakao.token-uri}") String tokenURI, MemberRepository memberRepository, JwtUtil jwtUtil) {
        this.clientId = clientId;
        this.tokenURI = tokenURI;
        this.userInfoURI = userInfoURI;
        this.redirectURI = redirectURI;
        this.memberRepository = memberRepository;
        this.jwtUtil = jwtUtil;
    }

    public String getAccessTokenFromKakao(String code) {

        KakaoTokenResponseDto kakaoTokenResDto = WebClient.create(tokenURI)
                .post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", clientId)
                        .queryParam("redirect_uri", redirectURI)
                        .queryParam("code", code)
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResponseDto.class)
                .block();


        log.info(" [Kakao Service] Access Token ------> {}", kakaoTokenResDto.accessToken());
        log.info(" [Kakao Service] Refresh Token ------> {}", kakaoTokenResDto.refreshToken());
        //제공 조건: OpenID Connect가 활성화 된 앱의 토큰 발급 요청인 경우 또는 scope에 openid를 포함한 추가 항목 동의 받기 요청을 거친 토큰 발급 요청인 경우
        log.info(" [Kakao Service] Id Token ------> {}", kakaoTokenResDto.idToken());
        log.info(" [Kakao Service] Scope ------> {}", kakaoTokenResDto.scope());

        return kakaoTokenResDto.accessToken();
    }

    public KakaoUserInfoResponseDto getUserInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo = WebClient.create(userInfoURI)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                //TODO : Custom Exception
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoUserInfoResponseDto.class)
                .block();

        log.info("[ Kakao Service ] Auth ID ---> {} ", userInfo.id());
        log.info("[ Kakao Service ] NickName ---> {} ", userInfo.kakaoAccount().profile().nickName());
        log.info("[ Kakao Service ] ProfileImageUrl ---> {} ", userInfo.kakaoAccount().profile().profileImageUrl());

        return userInfo;
    }
}