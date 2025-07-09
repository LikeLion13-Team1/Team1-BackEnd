package com.project.likelion13th_team1.global.feature.exception;

import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum FeatureErrorCode implements BaseErrorCode {

    FEATURE_NOT_FOUND(HttpStatus.NOT_FOUND, "FEATURE404_1", "특성을 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
