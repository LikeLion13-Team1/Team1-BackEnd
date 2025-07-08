package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;

public interface RoutineCommandService {
    RoutineResponseDto.RoutineCreateResponseDto createRoutine(Long groupId, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto);

    RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto);

    void deleteRoutine(String email, Long routineId);
}
