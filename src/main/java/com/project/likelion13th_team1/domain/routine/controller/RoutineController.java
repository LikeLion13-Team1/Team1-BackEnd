package com.project.likelion13th_team1.domain.routine.controller;

import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.service.command.RoutineCommandService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/routines")
@Tag(name = "Routine", description = "루틴 관련 API")
public class RoutineController {

    private final RoutineCommandService routineCommandService;

    @Operation(summary = "루틴 생성")
    @PostMapping()
    public CustomResponse<RoutineResponseDto.RoutineCreateResponseDto> createRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto
    ) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, routineCommandService.createRoutine(customUserDetails.getUsername(), routineCreateRequestDto));
    }

    @Operation(summary = "루틴 수정")
    @PatchMapping("/{routineId}")
    public CustomResponse<RoutineResponseDto.RoutineUpdateResponseDto> updateRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId,
            @RequestBody RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto

    ) {
        return CustomResponse.onSuccess(routineCommandService.updateRoutine(customUserDetails.getUsername(), routineId, routineUpdateRequestDto));
    }
}
