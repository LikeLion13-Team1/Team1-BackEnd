package com.project.likelion13th_team1.domain.alarm.service.command;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;
import com.project.likelion13th_team1.domain.alarm.entity.Activation;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.alarm.exception.AlarmErrorCode;
import com.project.likelion13th_team1.domain.alarm.exception.AlarmException;
import com.project.likelion13th_team1.domain.alarm.repository.AlarmRepository;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmCommandServiceImpl implements AlarmCommandService {
    private final AlarmRepository alarmRepository;
    private final RoutineRepository routineRepository;

    // Id만 넘기면 되므로 컨버터 미사용
    public Long updateAlarm(Long id , AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new AlarmException(AlarmErrorCode.ALARM_NOT_FOUND));

        alarm.setContext(alarmUpdateRequestDto.context());
        alarm.setActivation(Activation.valueOf(alarmUpdateRequestDto.activation()));
        alarm.setTime(alarmUpdateRequestDto.time());

        return alarm.getId();
    }

    public Long createAlarm(Long routineEventId, AlarmRequestDto.AlarmCreateRequestDto alarmCreateRequestDto) {
        // Routine 탐색
        Routine routine = routineRepository.findById(routineEventId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        Alarm alarm = Alarm.builder()
                .context(alarmCreateRequestDto.context())
                .activation(Activation.Y)
                .time(alarmCreateRequestDto.time())
                .routine(routine)
                .build();

        return alarm.getId();
    }
}
