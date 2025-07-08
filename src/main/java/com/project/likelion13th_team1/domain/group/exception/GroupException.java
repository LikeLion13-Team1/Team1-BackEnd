package com.project.likelion13th_team1.domain.group.exception;

import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;

public class GroupException extends CustomException {
    public GroupException(GroupErrorCode errorCode) {
        super(errorCode);
    }
}
