package com.project.likelion13th_team1.domain.routine.dto.response;

import lombok.Builder;

public class RoutineResponseDto {

    @Builder
    public record RoutineCreateResponseDto(
            Long routineId
    ) {
    }
}
