package com.project.likelion13th_team1.domain.routine.dto.request;

import com.project.likelion13th_team1.domain.routine.entity.Cycle;
import com.project.likelion13th_team1.domain.routine.entity.Status;
import com.project.likelion13th_team1.domain.routine.entity.Type;

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
}
