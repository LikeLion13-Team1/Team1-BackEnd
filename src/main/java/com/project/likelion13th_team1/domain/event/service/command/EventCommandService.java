package com.project.likelion13th_team1.domain.event.service.command;

import com.project.likelion13th_team1.domain.event.dto.request.EventRequestDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;

public interface EventCommandService {

    int createEvent(Routine routine);

    EventResponseDto.EventUpdateResponseDto updateEvent(String email, Long eventId, EventRequestDto.EventUpdateRequestDto eventUpdateRequestDto);

    void deleteEvent(String email, Long eventId);
}
