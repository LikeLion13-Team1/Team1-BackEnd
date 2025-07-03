package com.project.likelion13th_team1.domain.event.service.query;

import com.project.likelion13th_team1.domain.event.converter.EventConverter;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.exception.EventErrorCode;
import com.project.likelion13th_team1.domain.event.exception.EventException;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class EventQueryServiceImpl implements EventQueryService{

    private final EventRepository eventRepository;

    @Override
    public EventResponseDto.EventDetailResponseDto getEvent(String email, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        if (!event.getRoutine().getMember().getEmail().equals(email)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        return EventConverter.toEventDetailResponseDto(event);
    }
}
