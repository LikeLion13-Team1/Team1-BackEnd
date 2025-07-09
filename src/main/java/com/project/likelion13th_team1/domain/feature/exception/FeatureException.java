package com.project.likelion13th_team1.domain.feature.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class FeatureException extends CustomException {
    public FeatureException(FeatureErrorCode errorCode) {
        super(errorCode);
    }
}
