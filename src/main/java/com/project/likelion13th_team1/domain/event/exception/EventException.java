package com.project.likelion13th_team1.domain.event.exception;

import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class EventException extends CustomException {
    public EventException(EventErrorCode errorCode) {
        super(errorCode);
    }
}

