package com.project.likelion13th_team1.domain.routine.dto.response;

import lombok.Builder;

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
}
