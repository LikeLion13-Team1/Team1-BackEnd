package com.project.likelion13th_team1.domain.alarm.service.command;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;

public interface AlarmCommandService {
    Long toggleAlarm(Long eventId);
    Long updateAlarm(Long id , AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto);
    Long createAlarm(Long eventId, AlarmRequestDto.AlarmCreateRequestDto alarmCreateRequestDto);
}
