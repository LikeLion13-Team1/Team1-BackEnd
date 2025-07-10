package com.project.likelion13th_team1.domain.group.exception;

import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements BaseErrorCode {

    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "GROUP404", "그룹을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
