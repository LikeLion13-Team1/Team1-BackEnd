package com.project.likelion13th_team1.global.mail.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class MailException extends CustomException {
    public MailException(MailErrorCode errorCode) {
        super(errorCode);
    }
}
