package com.project.likelion13th_team1.domain.event.dto.response;

import com.project.likelion13th_team1.global.entity.RoutineStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class EventResponseDto {

    @Builder
    public record EventDetailResponseDto(
            Long routineId,
            Long eventId,
            LocalDateTime scheduledAt,
            LocalDateTime doneAt,
            RoutineStatus routineStatus
    ) {
    }

    @Builder
    public record EventCursorResponseDto(
            List<EventDetailResponseDto> events,
            Long nextCursor,
            Boolean hasNextCursor
    ) {
    }
}
