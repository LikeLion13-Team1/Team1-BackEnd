package com.project.likelion13th_team1.domain.routine.service.query;

import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;

public interface RoutineQueryService {

    RoutineResponseDto.RoutineDetailResponseDto getRoutine(String email, Long routineId);
}
