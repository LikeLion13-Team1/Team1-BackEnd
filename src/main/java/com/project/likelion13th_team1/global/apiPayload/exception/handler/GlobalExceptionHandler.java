package com.project.likelion13th_team1.global.apiPayload.exception.handler;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.apiPayload.code.BaseErrorCode;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    //애플리케이션에서 발생하는 커스텀 예외를 처리
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResponse<Void>> handleCustomException(CustomException ex) {
        //예외가 발생하면 로그 기록
        log.warn("[ CustomException ]: {}", ex.getCode().getMessage());
        //커스텀 예외에 정의된 에러 코드와 메시지를 포함한 응답 제공
        return ResponseEntity.status(ex.getCode().getHttpStatus())
                .body(ex.getCode().getErrorResponse());
    }

    // MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.warn("[ Validation Error - MethodArgumentNotValidException ]: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED_DTO_FILED;
        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errors);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    // ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CustomResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        log.warn("[ Validation Error - ConstraintViolationException ]: {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String field = violation.getPropertyPath().toString();
            errors.put(field, violation.getMessage());
        });

        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED_PARAM;
        CustomResponse<Map<String, String>> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), errors);
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException formatEx) {
            Class<?> targetType = formatEx.getTargetType();
            if (targetType.isEnum()) {
//                String fieldName = formatEx.getPath().get(0).getFieldName();
                String invalidValue = formatEx.getValue().toString();
                BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED_ENUM;
                CustomResponse<?> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), invalidValue);
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
//                return ResponseEntity.badRequest().body(
//                        CustomResponse.onFailure(
//                                "ENUM_INVALID",
//                                "필드 '" + fieldName + "'에 잘못된 enum 값 '" + invalidValue + "'이(가) 입력되었습니다."
//                        )
//                );
            }
            // LocalDateTime 파싱 오류 처리

            if (targetType.equals(LocalDateTime.class)) {
                String invalidValue = formatEx.getValue().toString();
                BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED_LOCAL_DATE_TIME;
                CustomResponse<?> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage(), invalidValue);
                return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
//                return ResponseEntity
//                        .badRequest()
//                        .body(CustomResponse.onFailure(
//                                "INVALID_DATETIME_FORMAT",
//                                "날짜/시간 형식이 잘못되었습니다. 형식: yyyy-MM-dd'T'HH:mm:ss"
//                        ));
            }
        }
//        if (cause instanceof DateTimeParseException) {
//            return ResponseEntity
//                    .badRequest()
//                    .body(CustomResponse.onFailure(
//                            "INVALID_DATETIME_FORMAT",
//                            "날짜/시간 형식이 잘못되었습니다. 형식: yyyy-MM-dd'T'HH:mm:ss"
//                    ));
//        }
        BaseErrorCode errorCode = GeneralErrorCode.VALIDATION_FAILED_JSON;
        CustomResponse<?> errorResponse = CustomResponse.onFailure(errorCode.getCode(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(errorResponse);
//        return ResponseEntity.badRequest().body(
//                CustomResponse.onFailure(
//                        "INVALID_REQUEST_BODY",
//                        "요청 본문의 형식이 잘못되었거나 파싱할 수 없습니다."
//                )
//        );
    }


    // 그 외의 정의되지 않은 모든 예외 처리
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomResponse<String>> handleAllException(Exception ex) {
        log.error("[WARNING] Internal Server Error : {} ", ex.getMessage());
        BaseErrorCode errorCode = GeneralErrorCode.INTERNAL_SERVER_ERROR_500;
        CustomResponse<String> errorResponse = CustomResponse.onFailure(
                errorCode.getCode(),
                errorCode.getMessage(),
                null
        );
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}
