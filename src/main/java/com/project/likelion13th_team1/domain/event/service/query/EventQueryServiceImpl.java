package com.project.likelion13th_team1.domain.event.service.query;

import com.project.likelion13th_team1.domain.event.converter.EventConverter;
import com.project.likelion13th_team1.domain.event.dto.EventDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.exception.EventErrorCode;
import com.project.likelion13th_team1.domain.event.exception.EventException;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Transactional
@RequiredArgsConstructor
public class EventQueryServiceImpl implements EventQueryService{

    private final EventRepository eventRepository;

    @Override
    public EventResponseDto.EventDetailResponseDto getEvent(String email, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        if (!event.getRoutine().getGroup().getMember().getEmail().equals(email)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        return EventConverter.toEventDetailResponseDto(event);
    }

    @Override
    public EventResponseDto.EventCursorResponseDto getEventCursorByDate(String email, Long cursor, Integer size, LocalDate today, LocalDate end) {
        Pageable pageable = PageRequest.of(0, size);

        // cursor가 0일 경우(첫페이지) cursor 최대값
        if (cursor == 0) {
            cursor = Long.MIN_VALUE;
        }

        Slice<EventDto> eventDtosSlice
                = eventRepository.findAllGreaterThanAndScheduledAtBetweenOrderByScheduledAtAsc(email, cursor, today, end, pageable);

        return EventConverter.toEventCursorResponseDto(eventDtosSlice);
    }

    @Override
    public EventResponseDto.EventCursorResponseDto getEventCursorByRoutine(String email, Long routineId, Long cursor, Integer size) {
        Pageable pageable = PageRequest.of(0, size);

        // cursor가 0일 경우(첫페이지) cursor 최대값
        if (cursor == 0) {
            cursor = Long.MIN_VALUE;
        }

        Slice<EventDto> eventDtosSlice
                = eventRepository.findAllByRoutineIdOrderByScheduledAtAsc(email, routineId, cursor, pageable);

        return EventConverter.toEventCursorResponseDto(eventDtosSlice);
    }
}
