package com.project.likelion13th_team1.domain.event.converter;

import com.project.likelion13th_team1.domain.event.dto.EventDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.global.entity.Status;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventConverter {

    public static Event toEvent(Routine routine, LocalDate date) {
        return Event.builder()
                .scheduledAt(date)
                .status(Status.PROCESSING)
                .routine(routine)
                .build();
    }

    public static EventResponseDto.EventDetailResponseDto toEventDetailResponseDto(Event event) {
        return EventResponseDto.EventDetailResponseDto.builder()
                .routineId(event.getRoutine().getId())
                .eventId(event.getId())
                .routineName(event.getRoutine().getName())
                .scheduledAt(event.getScheduledAt())
                .doneAt(event.getDoneAt())
                .status(event.getStatus())
                .build();
    }

    public static EventResponseDto.EventCursorResponseDto toEventCursorResponseDto(Slice<EventDto> eventDtosSlice) {
        // 이벤트 객체 자체를 담는 슬라이스를 가져와서 그 안의 모든 이벤트 객체들을 각각
        // 이벤트 컨버터에서 상세 조회 Dto 변환 -> 리스트화 해서 eventList로 변환

        List<EventResponseDto.EventDetailResponseDto> eventList = eventDtosSlice.stream()
                .map(eventDto -> toEventDetailResponseDto(eventDto.event()))
                .toList();

        // 다음 cursor 지정
        Long nextCursor = null;
        if (!eventDtosSlice.isEmpty() && eventDtosSlice.hasNext()) {
            nextCursor = eventDtosSlice.getContent().get(eventDtosSlice.getNumberOfElements() - 1).event().getId();
        }

        return EventResponseDto.EventCursorResponseDto.builder()
                .events(eventList)
                .hasNextCursor(eventDtosSlice.hasNext())
                .nextCursor(nextCursor)
                .build();

    }

    public static EventResponseDto.EventUpdateResponseDto toEventUpdateResponseDto(Event event) {
        return EventResponseDto.EventUpdateResponseDto.builder()
                .eventId(event.getId())
                .build();
    }

    public static EventResponseDto.EventDoneResponseDto toEventDoneResponseDto(Event event) {
        return EventResponseDto.EventDoneResponseDto.builder()
                .eventId(event.getId())
                .doneAt(event.getDoneAt())
                .build();
    }
}
