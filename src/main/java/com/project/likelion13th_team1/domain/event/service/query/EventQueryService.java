package com.project.likelion13th_team1.domain.event.service.query;

import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;

public interface EventQueryService {

    EventResponseDto.EventDetailResponseDto getEvent(String email, Long eventId);
}
