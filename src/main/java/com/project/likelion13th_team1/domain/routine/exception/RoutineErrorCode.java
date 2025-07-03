package com.project.likelion13th_team1.domain.routine.exception;

import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoutineErrorCode implements BaseErrorCode {

    ROUTINE_NOT_FOUND(HttpStatus.NOT_FOUND, "ROUTINE404", "루틴을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}