package com.project.likelion13th_team1.domain.member.exception;

import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER404_1", "회원을 찾을 수 없습니다."),
    MEMBER_EMAIL_DUPLICATE(HttpStatus.CONFLICT, "MEMBER409_1", "이미 사용 중인 이메일 입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
