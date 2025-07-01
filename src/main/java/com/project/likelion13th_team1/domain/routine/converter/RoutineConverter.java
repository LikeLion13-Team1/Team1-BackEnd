package com.project.likelion13th_team1.domain.routine.converter;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutineConverter {
    public static Routine toRoutine(RoutineRequestDto.RoutineCreateRequestDto dto, Member member) {
        return Routine.builder()
                .name(dto.name())
                .description(dto.description())
                .status(dto.status())
                .type(dto.type())
                .cycle(dto.cycle())
                .startAt(dto.startAt())
                .endAt(dto.endAt())
                .member(member)
                .build();
    }

    public static RoutineResponseDto.RoutineCreateResponseDto toRoutineCreateResponseDto(Routine routine) {
        return RoutineResponseDto.RoutineCreateResponseDto.builder()
                .routineId(routine.getId())
                .build();
    }
}
