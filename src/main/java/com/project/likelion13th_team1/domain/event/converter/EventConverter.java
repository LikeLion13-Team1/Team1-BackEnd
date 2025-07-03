package com.project.likelion13th_team1.domain.event.converter;

import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.global.entity.RoutineStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventConverter {

    public static Event toEvent(Routine routine, LocalDateTime date) {
        return Event.builder()
                .scheduledAt(date)
                .routineStatus(RoutineStatus.PROCESSING)
                .routine(routine)
                .build();
    }

    public static EventResponseDto.EventDetailResponseDto toEventDetailResponseDto(Event event) {
        return EventResponseDto.EventDetailResponseDto.builder()
                .routineId(event.getRoutine().getId())
                .eventId(event.getId())
                .scheduledAt(event.getScheduledAt())
                .doneAt(event.getDoneAt())
                .routineStatus(event.getRoutineStatus())
                .build();
    }
}
