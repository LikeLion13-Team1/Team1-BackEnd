package com.project.likelion13th_team1.domain.alarm.service.query;

import com.project.likelion13th_team1.domain.alarm.converter.AlarmConverter;
import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmResponseDto;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.alarm.repository.AlarmRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmQueryServiceImpl implements AlarmQueryService {
    private final AlarmRepository alarmRepository;
    private final AlarmConverter alarmConverter;

    public AlarmResponseDto.AlarmDetailResponseDto getAlarms(Long routineId, Long cursor, Integer size) {

        // Limit Size
        // hasNext 결정을 위해 기존 size에 +1 해서 요청
        Pageable pageable = PageRequest.of(0, size+1);

        List<Alarm> alarms = alarmRepository.findAlarmsByRoutineIdAndCursor(routineId, cursor, pageable);

        // hasNext 결정
        boolean hasNext = alarms.size() > size;
        if (hasNext) { alarms = alarms.subList(0, size); } // hasNext가 참일 시 마지막 인덱스 제거

        // alarms의 가장 마지막 원소의 id를 nextCursor로 결정
        Long nextCursor = alarms.isEmpty() ? null : alarms.get(alarms.size() - 1).getId();

        List<AlarmResponseDto.AlarmItem> alarmItems = alarmConverter.toAlarmItemList(alarms);
        return AlarmResponseDto.AlarmDetailResponseDto.builder()
                .alarms(alarmItems)
                .nextCursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }
}
