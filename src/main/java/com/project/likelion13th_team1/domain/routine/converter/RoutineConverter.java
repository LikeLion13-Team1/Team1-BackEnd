package com.project.likelion13th_team1.domain.routine.converter;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.entity.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutineConverter {
    public static Routine toRoutine(RoutineRequestDto.RoutineCreateRequestDto dto, Member member) {
        return Routine.builder()
                .name(dto.name())
                .description(dto.description())
                .status(dto.status())
                .type(Type.CUSTOM)
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

    // TODO : 이것도 합쳐야 함
    public static RoutineResponseDto.RoutineUpdateResponseDto toRoutineUpdateResponseDto(Routine routine) {
        return RoutineResponseDto.RoutineUpdateResponseDto.builder()
                .routineId(routine.getId())
                .build();
    }

    public static RoutineResponseDto.RoutineDetailResponseDto toRoutineDetailResponseDto(Routine routine) {
        return RoutineResponseDto.RoutineDetailResponseDto.builder()
                .routineId(routine.getId())
                .description(routine.getDescription())
                .status(routine.getStatus())
                .type(routine.getType())
                .cycle(routine.getCycle())
                .startAt(routine.getStartAt())
                .endAt(routine.getEndAt())
                .createdAt(routine.getCreatedAt())
                .build();
    }
}
