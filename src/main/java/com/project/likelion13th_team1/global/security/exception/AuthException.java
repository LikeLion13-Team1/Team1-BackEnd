package com.project.likelion13th_team1.global.security.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class AuthException extends CustomException {
    public AuthException(AuthErrorCode errorCode) {
        super(errorCode);
    }
}
