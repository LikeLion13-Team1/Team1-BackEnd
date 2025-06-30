package com.project.likelion13th_team1.global.security.auth.service;

import com.project.likelion13th_team1.global.security.jwt.dto.JwtDto;

public interface AuthService {
    JwtDto reissueToken(JwtDto jwtDto);
}
