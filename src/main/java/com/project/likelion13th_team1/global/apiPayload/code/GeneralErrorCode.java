package com.project.likelion13th_team1.global.apiPayload.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum GeneralErrorCode implements BaseErrorCode{

    BAD_REQUEST_400(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다"),

    UNAUTHORIZED_401(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다"),

    FORBIDDEN_403(HttpStatus.FORBIDDEN, "COMMON403", "접근이 금지되었습니다"),

    NOT_FOUND_404(HttpStatus.NOT_FOUND, "COMMON404", "요청한 자원을 찾을 수 없습니다"),

    INTERNAL_SERVER_ERROR_500(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 내부 오류가 발생했습니다"),

    // 유효성 검사
    VALIDATION_FAILED_DTO_FILED(HttpStatus.BAD_REQUEST, "VALID400_1", "잘못된 ReqDTO 입니다."),
    VALIDATION_FAILED_PARAM(HttpStatus.BAD_REQUEST, "VALID400_2", "잘못된 파라미터 입니다."),
    VALIDATION_FAILED_ENUM(HttpStatus.BAD_REQUEST, "ENUM400", "잘못된 ENUM 입력 입니다."),
    VALIDATION_FAILED_LOCAL_DATE_TIME(HttpStatus.BAD_REQUEST, "LOCAL_DATE_TIME400", "잘못된 날짜 시간 형식입니다. 요구 형식 -> YYYY-MM-DDTHH-MM-SS"),
    VALIDATION_FAILED_JSON(HttpStatus.BAD_REQUEST, "JSON400", "요청 본문의 형식이 잘못되었거나 파싱할 수 없습니다."),
    ;

    // 필요한 필드값 선언
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}