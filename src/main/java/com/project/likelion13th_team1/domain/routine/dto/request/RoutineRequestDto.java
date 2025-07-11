package com.project.likelion13th_team1.domain.routine.dto.request;

import com.project.likelion13th_team1.domain.routine.entity.Cycle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoutineRequestDto {

    public record RoutineCreateRequestDto(
            @NotBlank String name,
            @NotBlank String description,
//            Status status,
            @NotNull Boolean isActive,
            Cycle cycle,
            @NotNull LocalDate startAt,
            LocalDate endAt
    ) {
    }

    // TODO : 업데이트에 널이 들어가는 문제
    public record RoutineUpdateRequestDto(
            @NotBlank String name,
            @NotBlank String description,
//            Status status,
            Cycle cycle,
            @NotNull LocalDate startAt,
            LocalDate endAt
    ) {
    }
}
