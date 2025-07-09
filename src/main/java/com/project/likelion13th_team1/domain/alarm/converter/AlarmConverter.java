package com.project.likelion13th_team1.domain.alarm.converter;

import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmResponseDto;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class AlarmConverter {
    public AlarmResponseDto.AlarmItem toAlarmItem(Alarm alarm) {
        return AlarmResponseDto.AlarmItem.builder()
                .alarmId(alarm.getId())
                .context(alarm.getContext())
                .activation(alarm.getActivation().name()) // 문자열
                .time(alarm.getTime())
                .build();
    }

    public List<AlarmResponseDto.AlarmItem> toAlarmItemList(List<Alarm> alarms) {
        return alarms.stream()
                .map(this::toAlarmItem)
                .toList();
    }
}
