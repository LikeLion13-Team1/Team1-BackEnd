package com.project.likelion13th_team1.domain.alarm.service.command;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;
import com.project.likelion13th_team1.domain.alarm.entity.Activation;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.alarm.exception.AlarmErrorCode;
import com.project.likelion13th_team1.domain.alarm.exception.AlarmException;
import com.project.likelion13th_team1.domain.alarm.repository.AlarmRepository;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.event.exception.EventErrorCode;
import com.project.likelion13th_team1.domain.event.exception.EventException;
import com.project.likelion13th_team1.domain.event.repository.EventRepository;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AlarmCommandServiceImpl implements AlarmCommandService {
    private final AlarmRepository alarmRepository;
    private final RoutineRepository routineRepository;
    private final EventRepository eventRepository;

    // Id만 넘기면 되므로 컨버터 미사용
    public Long updateAlarm(Long id , AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new AlarmException(AlarmErrorCode.ALARM_NOT_FOUND));

        alarm.setContext(alarmUpdateRequestDto.context());
        alarm.setActivation(Activation.valueOf(alarmUpdateRequestDto.activation()));
        alarm.setTime(alarmUpdateRequestDto.time());

        return alarm.getId();
    }

    public Long createAlarm(Long eventId, AlarmRequestDto.AlarmCreateRequestDto alarmCreateRequestDto) {
        // Event 탐색
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));

        Alarm alarm = Alarm.builder()
                .context(alarmCreateRequestDto.context())
                .activation(Activation.Y)
                .time(alarmCreateRequestDto.time())
                .event(event)
                .build();

        Alarm saveAlarm = alarmRepository.save(alarm);

        return alarm.getId();
    }

    @Transactional
    public void autoCreateAlarm(List<Event> events) {
        log.info("이벤트 리스트 = {}", events);
        List<Alarm> alarmsToSave = events.stream()
                .map(event -> Alarm.builder()
                        .event(event)
                        .context("Normal")
                        .activation(Activation.Y)
                        .time(event.getScheduledAt().atTime(18, 0))
                        .build())
                .toList();
        log.info("저장할 알람 = {}", alarmsToSave);
        alarmRepository.saveAll(alarmsToSave);
        log.info("알람 저장 완료");
    }

    public Long toggleAlarm(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventException(EventErrorCode.EVENT_NOT_FOUND));
        Alarm alarm = alarmRepository.findByEvent(event)
                .orElseThrow(() -> new AlarmException(AlarmErrorCode.ALARM_NOT_FOUND));

        alarm.setActivation(alarm.getActivation().toggle());
        alarmRepository.save(alarm);

        return alarm.getId();
    }
}
