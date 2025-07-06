package com.project.likelion13th_team1.domain.alarm.dto.response;

import com.project.likelion13th_team1.domain.alarm.entity.Activation;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

public class AlarmResponseDto {

    @Builder
    public record AlarmDetailResponseDto(
            List<AlarmItem> alarms,
            Long nextCursor,
            boolean hasNext
    ) {}

    // 알람 리스트
    @Builder
    public record AlarmItem(
            Long alarmId,
            String context,
            String activation,
            LocalDateTime time
    ) {}


    @Builder
    public record AlarmUpdateResponseDto(
            Long alarmId
    ) {}
}
