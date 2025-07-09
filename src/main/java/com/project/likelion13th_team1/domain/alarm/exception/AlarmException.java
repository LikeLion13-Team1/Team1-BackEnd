package com.project.likelion13th_team1.domain.alarm.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class AlarmException extends CustomException {
    public AlarmException(AlarmErrorCode alarmErrorCode) {
        super(alarmErrorCode);
    }
}
