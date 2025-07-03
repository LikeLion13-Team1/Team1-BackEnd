package com.project.likelion13th_team1.domain.event.controller;

import com.project.likelion13th_team1.domain.event.dto.request.EventRequestDto;
import com.project.likelion13th_team1.domain.event.dto.response.EventResponseDto;
import com.project.likelion13th_team1.domain.event.service.query.EventQueryService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
@Tag(name = "Event", description = "루틴 이벤트 관련 API")
public class EventController {

    private final EventQueryService eventQueryService;

    @Operation(summary = "루틴 이벤트 단일 조회")
    @GetMapping("/{eventId}")
    public CustomResponse<EventResponseDto.EventDetailResponseDto> getEvent(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long eventId
    ) {
        return CustomResponse.onSuccess(eventQueryService.getEvent(customUserDetails.getUsername(), eventId));
    }
}
