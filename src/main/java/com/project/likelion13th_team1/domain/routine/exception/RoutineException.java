package com.project.likelion13th_team1.domain.routine.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class RoutineException extends CustomException {
    public RoutineException(RoutineErrorCode message) {
        super(message);
    }
}
