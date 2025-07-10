package com.project.likelion13th_team1.global.security.filter;

import com.project.likelion13th_team1.global.security.jwt.JwtUtil;
import com.project.likelion13th_team1.global.security.jwt.entity.Token;
import com.project.likelion13th_team1.global.security.jwt.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = jwtUtil.resolveAccessToken(request);

        jwtUtil.validateToken(accessToken);

        // 로그아웃 블랙리스트 등록
        long expiration = jwtUtil.getExpiration(accessToken);
        log.info("[ Redis 저장 ] key = Logout {}, 남은시간 = {}", accessToken, expiration);
        redisTemplate.opsForValue().set("Logout " + accessToken, "logout", expiration, TimeUnit.MILLISECONDS);
        log.info("[ CustomLogoutHandler ] Logout 블랙리스트 등록 완료");

        String email = jwtUtil.getEmail(accessToken);
        Optional<Token> token = tokenRepository.findByEmail(email);
        token.ifPresent(tokenRepository::delete);
        log.info("[ CustomLogoutHandler ] 블랙리스트 RefreshToken 삭제 완료");
    }
}