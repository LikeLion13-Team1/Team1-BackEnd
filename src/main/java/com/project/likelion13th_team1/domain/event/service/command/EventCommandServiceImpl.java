package com.project.likelion13th_team1.domain.event.service.command;

import com.project.likelion13th_team1.domain.event.converter.EventConverter;
import com.project.likelion13th_team1.domain.event.dto.request.EventRequestDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.exception.EventErrorCode;
import com.project.likelion13th_team1.domain.event.exception.EventException;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class EventCommandServiceImpl implements EventCommandService {

    private final EventRepository eventRepository;

    @Override
    public int createEvent(Routine routine) {

        LocalDateTime start = routine.getStartAt();
        LocalDateTime end = routine.getEndAt();
        long cycle = routine.getCycle().getDays();
        int eventCount = 0;

//        // 루틴 반복 시작 시간과 반복 끝 시간이 며칠인지 계산 후, cycle이 몇 번 들어갈 수 있는지 확인후 생성
//        for (LocalDateTime date = start; !date.isAfter(end); date = date.plusDays(cycle)) {
//            Event event = EventConverter.toEvent(routine, date);
//            eventRepository.save(event);
//            eventCount++;
//        }

        Set<LocalDateTime> existingDates = new HashSet<>(
                eventRepository.findScheduledDatesByRoutineAndStartBetweenEnd(routine, start, end)
        );

        List<Event> eventsToSave = new ArrayList<>();

        if (cycle == 0) {
            if (!existingDates.contains(start)) {
                Event event = EventConverter.toEvent(routine, start);
                eventsToSave.add(event);
            }

            eventRepository.saveAll(eventsToSave);
            return eventsToSave.size();  // 0 또는 1 반환
        }

        for (LocalDateTime date = start; !date.isAfter(end); date = date.plusDays(cycle)) {
            if (!existingDates.contains(date)) {
                Event event = EventConverter.toEvent(routine, date);
                eventsToSave.add(event);

                eventCount++;
            }
        }
        eventRepository.saveAll(eventsToSave);

        return eventCount;
    }

    @Override
    public EventResponseDto.EventUpdateResponseDto updateEvent(String email, Long eventId, EventRequestDto.EventUpdateRequestDto eventUpdateRequestDto) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        if (!event.getRoutine().getMember().getEmail().equals(email)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        event.updateEvent(eventUpdateRequestDto.scheduledAt(), eventUpdateRequestDto.doneAt(), eventUpdateRequestDto.status());
        return EventConverter.toEventUpdateResponseDto(event);
    }

    @Override
    public void deleteEvent(String email, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        if (!event.getRoutine().getMember().getEmail().equals(email)) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        eventRepository.deleteById(eventId);
    }

    @Override
    public void deleteOrphanedEvent(Routine routine) {
        eventRepository.deleteByRoutine(routine);
    }

    @Override
    public EventResponseDto.EventDoneResponseDto doneEvent(String email, Long eventId) {
        Event event = eventRepository.findByIdAndEmail(eventId, email)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        event.doneEvent();
        return EventConverter.toEventDoneResponseDto(event);
    }

    @Override
    public EventResponseDto.EventDoneResponseDto undoneEvent(String email, Long eventId) {
        Event event = eventRepository.findByIdAndEmail(eventId, email)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        event.undoneEvent();
        return EventConverter.toEventDoneResponseDto(event);
    }
}
