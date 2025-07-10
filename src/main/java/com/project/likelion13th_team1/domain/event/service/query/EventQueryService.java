package com.project.likelion13th_team1.domain.event.service.query;

import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface EventQueryService {

    EventResponseDto.EventDetailResponseDto getEvent(String email, Long eventId);

    EventResponseDto.EventCursorResponseDto getEventCursorByDate(String email, Long cursor, Integer size, LocalDate today, LocalDate end);

    EventResponseDto.EventCursorResponseDto getEventCursorByRoutine(String email, Long routineId, Long cursor, Integer size);

}