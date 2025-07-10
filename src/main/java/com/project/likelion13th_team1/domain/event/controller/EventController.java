package com.project.likelion13th_team1.domain.event.controller;

import com.project.likelion13th_team1.domain.event.dto.request.EventRequestDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.service.command.EventCommandService;
import com.project.likelion13th_team1.domain.event.service.query.EventQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Tag(name = "Event", description = "루틴 이벤트 관련 API")
public class EventController {

    private final EventCommandService eventCommandService;
    private final EventQueryService eventQueryService;

    @Operation(summary = "루틴 이벤트 단일 조회", description = "이벤트 아이디로 이벤트 객체 1개를 검색한다.")
    @GetMapping("/{eventId}")
    public CustomResponse<EventResponseDto.EventDetailResponseDto> getEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId
    ) {
        return CustomResponse.onSuccess(eventQueryService.getEvent(customUserDetails.getUsername(), eventId));
    }

    @Operation(summary = "루틴 이벤트 목록 커서 조회 (날짜 검색)", description = "cursor은 커서 위치로 맨 초기에는 0을 입력한다<br>" +
            "size는 한번에 나타낼 객체의 개수이다.<br>" +
            "hasNextCursor가 true라면 뒤에 내용이 더 있다는 의미이므로 다음 커서를 nextCursor값으로 입력하면 계속해서 객체가 출력된다." +
            "<br> 날짜를 기준으로 검색하므로 YYYY-MM-DD 의 형태로 검색 시작 날짜와 끝 날짜를 입력한다.")
    @GetMapping()
    public CustomResponse<EventResponseDto.EventCursorResponseDto> getEventCursor(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long cursor,
            @RequestParam Integer size,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate end
    ) {
        return CustomResponse.onSuccess(eventQueryService.getEventCursor(customUserDetails.getUsername(), cursor, size, start, end));
    }

    @Operation(summary = "루틴 이벤트 수정", description = "이벤트가 수행될 시간 scheduledAt은 null 일 수 없다.")
    @PatchMapping("/{eventId}")
    public CustomResponse<EventResponseDto.EventUpdateResponseDto> updateEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId,
            @RequestBody @Valid EventRequestDto.EventUpdateRequestDto eventUpdateRequestDto
    ) {
        return CustomResponse.onSuccess(eventCommandService.updateEvent(customUserDetails.getUsername(), eventId, eventUpdateRequestDto));
    }

    @Operation(summary = "루틴 이벤트 삭제", description = "이벤트 아이디를 통해 이벤트 객체를 삭제한다.")
    @DeleteMapping("/{eventId}")
    public CustomResponse<String> deleteEvent(
        @AuthenticationPrincipal CustomUserDetails customUserDetails,
        @PathVariable Long eventId
    ) {
        eventCommandService.deleteEvent(customUserDetails.getUsername(), eventId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "루틴 이벤트 삭제 완료");
    }

    @Operation(summary = "루틴 완료", description = "루틴을 수행했을 때, 루틴 수행완료 시간을 현재 시간으로 설정하고, 해당 status를 SUCCESS로 변경한다.")
    @PatchMapping("/{eventId}/done")
    public CustomResponse<EventResponseDto.EventDoneResponseDto> doneEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId
    ) {
        return CustomResponse.onSuccess(eventCommandService.doneEvent(customUserDetails.getUsername(), eventId));
    }

    @Operation(summary = "루틴 완료 취소", description = "수행한 루틴의 수행완료를 되돌리고 싶을 때, 루틴 수행완료 시간을 null로 설정하고, 해당 status를 PROCESSING으로 변경한다.")
    @PatchMapping("/{eventId}/undone")
    public CustomResponse<EventResponseDto.EventDoneResponseDto> undoneEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId
    ) {
        return CustomResponse.onSuccess(eventCommandService.undoneEvent(customUserDetails.getUsername(), eventId));
    }
}
