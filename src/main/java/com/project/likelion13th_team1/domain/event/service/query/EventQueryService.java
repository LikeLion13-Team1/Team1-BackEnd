package com.project.likelion13th_team1.domain.event.service.query;

import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;

import java.time.LocalDateTime;

public interface EventQueryService {

    EventResponseDto.EventDetailResponseDto getEvent(String email, Long eventId);

    EventResponseDto.EventCursorResponseDto getEventCursor(String email, Long cursor, Integer size, LocalDateTime start, LocalDateTime end);
}