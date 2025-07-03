package com.project.likelion13th_team1.domain.event.service.command;

import com.project.likelion13th_team1.domain.event.converter.EventConverter;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class EventCommandServiceImpl implements EventCommandService {

    private final EventRepository eventRepository;

    @Override
    public int createEvent(Routine routine) {

        LocalDateTime start = routine.getStartAt();
        LocalDateTime end = routine.getEndAt();
        long cycle = 7;
        int eventCount = 0;

        // 루틴 반복 시작 시간과 반복 끝 시간이 며칠인지 계산 후, cycle이 몇 번 들어갈 수 있는지 확인후 생성
        for (LocalDateTime date = start; !date.isAfter(end); date = date.plusDays(cycle)) {
            Event event = EventConverter.toEvent(routine, date);
            eventRepository.save(event);
            eventCount++;
        }

        return eventCount;
    }
}
