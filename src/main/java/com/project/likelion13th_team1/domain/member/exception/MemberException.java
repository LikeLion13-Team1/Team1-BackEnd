package com.project.likelion13th_team1.domain.member.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class MemberException extends CustomException {
    public MemberException(MemberErrorCode errorCode) {
        super(errorCode);
    }
}
