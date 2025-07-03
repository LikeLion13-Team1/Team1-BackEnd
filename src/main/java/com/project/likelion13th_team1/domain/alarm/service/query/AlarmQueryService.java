package com.project.likelion13th_team1.domain.alarm.service.query;

import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmResponseDto;

public interface AlarmQueryService {
    AlarmResponseDto.AlarmDetailResponseDto getAlarms(Long routineEventId, Long cursor, Integer size);
}
