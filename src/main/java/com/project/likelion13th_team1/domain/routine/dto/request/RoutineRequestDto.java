package com.project.likelion13th_team1.domain.routine.dto.request;

import com.project.likelion13th_team1.domain.routine.entity.Cycle;
import com.project.likelion13th_team1.global.entity.Status;

import java.time.LocalDateTime;

public class RoutineRequestDto {

    public record RoutineCreateRequestDto(
            String name,
            String description,
            Status status,
            Cycle cycle,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }

    // TODO : 이것도 합치면 될듯?
    public record RoutineUpdateRequestDto(
            String name,
            String description,
            Status status,
            Cycle cycle,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
    }
}
