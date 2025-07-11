package com.project.likelion13th_team1.domain.alarm.service.query;

import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmDto;
import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmResponseDto;
import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;

import java.util.List;

public interface AlarmQueryService {
    AlarmResponseDto.AlarmDetailResponseDto getAlarms1(Long routineEventId, Long cursor, Integer size);
    List<Alarm> getAlarms(CustomUserDetails customUserDetails);
    List<AlarmDto> getAlarmsByMember(Member member);
}
