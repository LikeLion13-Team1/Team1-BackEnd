package com.project.likelion13th_team1.global.security.auth.service;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.security.auth.dto.request.AuthRequestDto;
import com.project.likelion13th_team1.global.security.auth.entity.Auth;
import com.project.likelion13th_team1.global.security.exception.AuthErrorCode;
import com.project.likelion13th_team1.global.security.exception.AuthException;
import com.project.likelion13th_team1.global.security.jwt.JwtUtil;
import com.project.likelion13th_team1.global.security.jwt.dto.JwtDto;
import com.project.likelion13th_team1.global.security.jwt.entity.Token;
import com.project.likelion13th_team1.global.security.jwt.repository.TokenRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService{

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public JwtDto reissueToken(JwtDto tokenDto) {
        log.info("[ Auth Service ] 토큰 재발급을 시작합니다.");
        String accessToken = tokenDto.accessToken();
        String refreshToken = tokenDto.refreshToken();

        //Access Token 으로부터 사용자 Email 추출
        String email = jwtUtil.getEmail(refreshToken); // **수정부분**
        log.info("[ Auth Service ] Email ---> {}", email);

        //Access Token 에서의 Email 로 부터 DB 에 저장된 Refresh Token 가져오기
        Token refreshTokenByDB = tokenRepository.findByEmail(email).orElseThrow(
                () -> new AuthException(AuthErrorCode.INVALID_TOKEN)
        );

        //Refresh Token 이 유효한지 검사
        jwtUtil.validateToken(refreshToken);

        log.info("[ Auth Service ] Refresh Token 이 유효합니다.");

        //만약 DB 에서 찾은 Refresh Token 과 파라미터로 온 Refresh Token 이 일치하면 새로운 토큰 발급
        if (refreshTokenByDB.getToken().equals(refreshToken)) {
            log.info("[ Auth Service ] 토큰을 재발급합니다.");
            return jwtUtil.reissueToken(refreshToken);
        } else {
            throw new AuthException(AuthErrorCode.INVALID_TOKEN);
        }
    }

    @Override
    public void resetPassword(String email, AuthRequestDto.PasswordResetRequestDto passwordResetRequestDto) {

        if (!passwordResetRequestDto.newPassword().equals(passwordResetRequestDto.newPasswordConfirmation())) {
            throw new AuthException(AuthErrorCode.NEW_PASSWORD_DOES_NOT_MATCH);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Auth auth = member.getAuth();

        if (!passwordEncoder.matches(passwordResetRequestDto.currentPassword(), auth.getPassword())) {
            throw new AuthException(AuthErrorCode.CURRENT_PASSWORD_DOES_NOT_MATCH);
        }
        if (passwordEncoder.matches(passwordResetRequestDto.newPassword(), auth.getPassword())) {
            throw new AuthException(AuthErrorCode.NEW_PASSWORD_IS_CURRENT_PASSWORD);
        }

        auth.updatePassword(passwordEncoder.encode(auth.getPassword()));

    }
}
