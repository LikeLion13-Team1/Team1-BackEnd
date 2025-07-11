package com.project.likelion13th_team1.domain.alarm.dto.request;

import lombok.Builder;

import java.time.LocalDateTime;

public class AlarmRequestDto {
    @Builder
    public record AlarmUpdateRequestDto(
            String context,
            String activation,
            LocalDateTime time
    ) {}

    @Builder
    public record AlarmCreateRequestDto(
       String context,
       LocalDateTime time
    ) {}
}
