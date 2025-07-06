package com.project.likelion13th_team1.domain.alarm.service.command;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;

public interface AlarmCommandService {
    Long updateAlarm(Long id , AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto);
}
