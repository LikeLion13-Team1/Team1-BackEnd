package com.project.likelion13th_team1.domain.event.dto.request;

import com.project.likelion13th_team1.global.entity.Status;

import java.time.LocalDateTime;

public class EventRequestDto {

    public record EventUpdateRequestDto(
            LocalDateTime scheduledAt,
            LocalDateTime doneAt,
            Status status
    ) {
    }
}
