package com.project.likelion13th_team1.domain.routine.service.query;

import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;

public interface RoutineQueryService {

    RoutineResponseDto.RoutineDetailResponseDto getRoutine(String email, Long routineId);

    RoutineResponseDto.RoutineCursorResponseDto getMyRoutineCursor(String email, Long cursor, Integer size);

    RoutineResponseDto.RoutineCursorResponseDto getMyGroupRoutineCursor(String email, Long groupId, Long cursor, Integer size);

    RoutineResponseDto.RoutineCursorResponseDto getMyGroupTrueRoutineCursor(String email, Long groupId, Long cursor, Integer size);
}
