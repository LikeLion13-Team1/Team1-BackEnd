package com.project.likelion13th_team1.domain.alarm.controller;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;
import com.project.likelion13th_team1.domain.alarm.service.command.AlarmCommandService;
import com.project.likelion13th_team1.domain.alarm.service.query.AlarmQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Alarm", description = "알람 관련 API")
public class AlarmController {
    private final AlarmQueryService alarmQueryService;
    private final AlarmCommandService alarmCommandService;

    @Operation(summary = "알람 조회")
    @GetMapping("/api/v1/routines/{routineEventId}/alarms")
    public CustomResponse<?> getAlarms(
            @PathVariable("routineEventId") Long routineEventId,
            @RequestParam("cursor") Long cursor,
            @RequestParam("size") Integer size
    ) {
        return CustomResponse.onSuccess(alarmQueryService.getAlarms(routineEventId, cursor, size));
    }

    @Operation
    @PatchMapping("/api/v1/alarms/{alarmId}")
    public CustomResponse<?> updateAlarm(
            @PathVariable("alarmId") Long alarmId,
            @RequestBody AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto
    ) {
        return CustomResponse.onSuccess(alarmCommandService.updateAlarm(alarmId, alarmUpdateRequestDto));
    }
}
