package com.project.likelion13th_team1.domain.routine.converter;

import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.entity.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RoutineConverter {
    public static Routine toRoutine(RoutineRequestDto.RoutineCreateRequestDto dto, Group group) {
        return Routine.builder()
                .name(dto.name())
                .description(dto.description())
//                .status(dto.status())
//                .type(Type.CUSTOM)
                .isActive(true)
//                .cycle(dto.cycle())
                .repeatDays(dto.repeatDays())
//                .startAt(dto.startAt())
//                .endAt(dto.endAt())
                .group(group)
//                .member(member)
                .build();
    }

    public static RoutineResponseDto.RoutineCreateResponseDto toRoutineCreateResponseDto(Routine routine, Integer eventCount) {
        return RoutineResponseDto.RoutineCreateResponseDto.builder()
                .routineId(routine.getId())
                .eventCount(eventCount)
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
                .groupId(routine.getGroup().getId())
                .routineId(routine.getId())
                .description(routine.getDescription())
//                .status(routine.getStatus())
//                .type(routine.getType())
//                .cycle(routine.getCycle())
                .repeatDays(routine.getRepeatDays())
//                .startAt(routine.getStartAt())
//                .endAt(routine.getEndAt())
                .createdAt(routine.getCreatedAt())
                .build();
    }

    public static RoutineResponseDto.RoutineCursorResponseDto toRoutineCursorResponseDto(Slice<RoutineDto> routineDtosSlice) {
        // 루틴 객체 자체를 담는 슬라이스를 가져와서 그 안의 모든 루틴 객체들을 각각
        // 루틴 컨버터에서 상세 조회 Dto 변환 -> 리스트화 해서 routineList로 변환

        List<RoutineResponseDto.RoutineDetailResponseDto> routineList = routineDtosSlice.stream()
                .map(routineDto -> toRoutineDetailResponseDto(routineDto.routine()))
                .toList();

        // 다음 cursor 지정
        Long nextCursor = null;
        if (!routineDtosSlice.isEmpty() && routineDtosSlice.hasNext()) {
            nextCursor = routineDtosSlice.getContent().get(routineDtosSlice.getNumberOfElements() - 1).routine().getId();
        }

        return RoutineResponseDto.RoutineCursorResponseDto.builder()
                .routines(routineList)
                .hasNextCursor(routineDtosSlice.hasNext())
                .nextCursor(nextCursor)
                .build();

    }
}
