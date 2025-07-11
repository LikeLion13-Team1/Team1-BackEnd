package com.project.likelion13th_team1.global.mail.exception;

import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements BaseErrorCode {

    WRONG_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "MAIL400_1", "메일 주소가 잘못되었습니다"),
    EMAIL_MANIPULATED(HttpStatus.BAD_REQUEST, "MAIL400_2", "인증 코드가 발급된 이메일이 아닙니다."),
    WRONG_CODE(HttpStatus.BAD_REQUEST, "CODE400", "코드 입력이 잘못되었습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
