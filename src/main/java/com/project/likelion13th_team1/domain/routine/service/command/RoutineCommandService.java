package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;

public interface RoutineCommandService {
    RoutineResponseDto.RoutineCreateResponseDto createRoutine(String email, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto);

    RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto);
}
