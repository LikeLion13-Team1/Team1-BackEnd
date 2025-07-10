package com.project.likelion13th_team1.domain.event.scheduler;

import com.project.likelion13th_team1.domain.event.converter.EventConverter;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {

    private final RoutineRepository routineRepository;
    private final EventRepository eventRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정
//    @Scheduled(fixedRate = 10000)
    public void generateRoutineEvents() {
        log.info("[Scheduler] 루틴 이벤트 생성 스케줄러 시작");
        LocalDate today = LocalDate.now();
        LocalDate yearLater = today.plusYears(1); // ✅ 생성 범위: 오늘 ~ 1년 뒤

        List<Routine> routines = routineRepository.findAllActiveRoutines(); // ✅ 활성 루틴만

        for (Routine routine : routines) {
            LocalDate start = routine.getStartAt();
            LocalDate end = routine.getEndAt() != null ? routine.getEndAt() : yearLater;
            long cycle = routine.getCycle().getDays(); // ✅ 예: 3일마다

            // endAt이 1년 뒤보다 늦으면 1년 뒤까지만 생성
            LocalDate generateUntil = end.isBefore(yearLater) ? end : yearLater;

            // ✅ 이미 생성된 날짜 추출
            Set<LocalDate> existingDates = new HashSet<>(
                    eventRepository.findScheduledDatesByRoutineAndStartBetweenEnd(routine, today, generateUntil)
            );

            List<Event> eventsToSave = new ArrayList<>();

            for (LocalDate date = start; !date.isAfter(generateUntil); date = date.plusDays(cycle)) {
                if (!date.isBefore(today) && !existingDates.contains(date)) {
                    Event event = EventConverter.toEvent(routine, date);
                    eventsToSave.add(event);
                    log.info("✅ 이벤트 생성 - routineId={}, date={}", routine.getId(), date);
                }
            }

            eventRepository.saveAll(eventsToSave);
        }

        log.info("[Scheduler] 루틴 이벤트 생성 스케줄러 종료");
    }
}
