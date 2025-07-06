package com.project.likelion13th_team1.domain.routine.dto.request;

import com.project.likelion13th_team1.domain.routine.entity.Cycle;
import com.project.likelion13th_team1.global.entity.RoutineStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RoutineRequestDto {

    public record RoutineCreateRequestDto(
            @NotBlank String name,
            @NotBlank String description,
            RoutineStatus routineStatus,
            Cycle cycle,
            @NotNull LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }

    // TODO : 업데이트에 널이 들어가는 문제
    public record RoutineUpdateRequestDto(
            @NotBlank String name,
            @NotBlank String description,
            RoutineStatus routineStatus,
            Cycle cycle,
            @NotNull LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }
}
