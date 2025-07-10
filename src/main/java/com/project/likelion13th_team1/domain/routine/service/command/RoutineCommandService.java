package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.ExampleRoutine;

public interface RoutineCommandService {
    RoutineResponseDto.RoutineCreateResponseDto createRoutine(Long groupId, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto);

    RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto);

    void createExampleRoutine(ExampleRoutine exampleRoutine, Group group);

    void deleteRoutine(String email, Long routineId);

    void activateRoutine(String email, Long routineId);

    void inactivateRoutine(String email, Long routineId);
}
