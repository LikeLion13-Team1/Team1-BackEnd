package com.project.likelion13th_team1.domain.alarm.controller;

import com.project.likelion13th_team1.domain.alarm.dto.request.AlarmRequestDto;
import com.project.likelion13th_team1.domain.alarm.dto.response.AlarmDto;
import com.project.likelion13th_team1.domain.alarm.service.command.AlarmCommandService;
import com.project.likelion13th_team1.domain.alarm.service.query.AlarmQueryService;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Alarm", description = "알람 관련 API")
public class AlarmController {
    private final AlarmQueryService alarmQueryService;
    private final AlarmCommandService alarmCommandService;
    private final MemberRepository memberRepository;

    @Operation(summary = "알람 전부 조회")
    @GetMapping("/api/v1/alarms")
    public ResponseEntity<List<AlarmDto>> getAlarms(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Member member = memberRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        List<AlarmDto> alarms = alarmQueryService.getAlarmsByMember(member);
        return ResponseEntity.ok(alarms);
    }

    @Operation(summary = "알람 활성화 토글")
    @PatchMapping("/api/v1/alarms/{eventId}")
    public CustomResponse<?> toggleAlarm(
            @PathVariable("eventId") Long eventId
    ) {
        return CustomResponse.onSuccess(alarmCommandService.toggleAlarm(eventId));
    }

    @Operation(summary = "알람 수정")
    @PatchMapping("/api/v1/alarms1/{alarmId}")
    public CustomResponse<?> updateAlarm(
            @PathVariable("alarmId") Long alarmId,
            @RequestBody AlarmRequestDto.AlarmUpdateRequestDto alarmUpdateRequestDto
    ) {
        return CustomResponse.onSuccess(alarmCommandService.updateAlarm(alarmId, alarmUpdateRequestDto));
    }

    @Operation(summary = "알람 생성")
    @PostMapping("/api/v1/events/{eventId}/alarms")
    public CustomResponse<?> createAlarm(
            @PathVariable("eventId") Long eventId,
            @RequestBody AlarmRequestDto.AlarmCreateRequestDto alarmCreateRequestDto
    ) {
        return CustomResponse.onSuccess(alarmCommandService.createAlarm(eventId, alarmCreateRequestDto));
    }
}
