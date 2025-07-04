package com.project.likelion13th_team1.domain.event.controller;

import com.project.likelion13th_team1.domain.event.dto.request.EventRequestDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.service.command.EventCommandService;
import com.project.likelion13th_team1.domain.event.service.query.EventQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Tag(name = "Event", description = "루틴 이벤트 관련 API")
public class EventController {

    private final EventCommandService eventCommandService;
    private final EventQueryService eventQueryService;

    @Operation(summary = "루틴 이벤트 단일 조회")
    @GetMapping("/{eventId}")
    public CustomResponse<EventResponseDto.EventDetailResponseDto> getEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId
    ) {
        return CustomResponse.onSuccess(eventQueryService.getEvent(customUserDetails.getUsername(), eventId));
    }

    @Operation(summary = "루틴 이벤트 목록 커서 조회 (날짜 검색)")
    @GetMapping()
    public CustomResponse<EventResponseDto.EventCursorResponseDto> getEventCursor(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long cursor,
            @RequestParam Integer size,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
    ) {
        return CustomResponse.onSuccess(eventQueryService.getEventCursor(customUserDetails.getUsername(), cursor, size, start, end));
    }

    @Operation(summary = "루틴 이벤트 수정")
    @PatchMapping("/{eventId}")
    public CustomResponse<EventResponseDto.EventUpdateResponseDto> updateEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId,
            @RequestBody EventRequestDto.EventUpdateRequestDto eventUpdateRequestDto
    ) {
        return CustomResponse.onSuccess(eventCommandService.updateEvent(customUserDetails.getUsername(), eventId, eventUpdateRequestDto));
    }

    @Operation(summary = "루틴 이벤트 삭제")
    @DeleteMapping("/{eventId}")
    public CustomResponse<String> deleteEvent(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long eventId
    ) {
        eventCommandService.deleteEvent(customUserDetails.getUsername(), eventId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "루틴 이벤트 삭제 완료");
    }

}
