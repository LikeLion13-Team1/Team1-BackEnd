package com.project.likelion13th_team1.domain.membership.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class MembershipException extends CustomException {
    public MembershipException(MembershipErrorCode errorCode) {
        super(errorCode);
    }
}
