package com.project.likelion13th_team1.domain.routine.dto.response;

import com.project.likelion13th_team1.domain.routine.entity.Cycle;
import com.project.likelion13th_team1.domain.routine.entity.Status;
import com.project.likelion13th_team1.domain.routine.entity.Type;
import lombok.Builder;

import java.time.LocalDateTime;

public class RoutineResponseDto {

    @Builder
    public record RoutineCreateResponseDto(
            Long routineId
    ) {
    }

    // TODO : 나중에 routineResponseDto 이렇게 합쳐도 될 듯?
    @Builder
    public record RoutineUpdateResponseDto(
            Long routineId
    ) {
    }

    @Builder
    public record RoutineDetailResponseDto(
            Long routineId,
            String description,
            Status status,
            Type type,
            Cycle cycle,
            LocalDateTime startAt,
            LocalDateTime endAt,
            LocalDateTime createdAt
    ) {
    }
}
