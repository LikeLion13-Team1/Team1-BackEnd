package com.project.likelion13th_team1.domain.event.dto.response;

import com.project.likelion13th_team1.global.entity.Status;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDto {

    @Builder
    public record EventDetailResponseDto(
            Long routineId,
            Long eventId,
            LocalDate scheduledAt,
            LocalDate doneAt,
            Status status
    ) {
    }

    @Builder
    public record EventCursorResponseDto(
            List<EventDetailResponseDto> events,
            Long nextCursor,
            Boolean hasNextCursor
    ) {
    }

    @Builder
    public record EventUpdateResponseDto(
            Long eventId
    ) {
    }

    @Builder
    public record EventDoneResponseDto(
            Long eventId,
            LocalDate doneAt
    ) {
    }
}
