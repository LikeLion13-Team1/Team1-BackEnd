package com.project.likelion13th_team1.domain.routine.service.query;

import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineQueryServiceImpl implements RoutineQueryService {

    private final RoutineRepository routineRepository;

    @Override
    public RoutineResponseDto.RoutineDetailResponseDto getRoutine(String email, Long routineId) {

        Routine routine = routineRepository.findByMemberEmailAndRoutineId(email, routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        return RoutineConverter.toRoutineDetailResponseDto(routine);
    }

    @Override
    public RoutineResponseDto.RoutineCursorResponseDto getRoutineCursor(String email, Long cursor, Integer size) {
        Pageable pageable = PageRequest.of(0, size);

        // cursor가 0일 경우(첫페이지) cursor 최대값
        if (cursor == 0) {
            cursor = Long.MAX_VALUE;
        }

        Slice<RoutineDto> routineDtosSlice
                = routineRepository.findAllByRoutineIdLessThanOrderByRoutineIdDesc(email, cursor, pageable);

        return RoutineConverter.toRoutineCursorResponseDto(routineDtosSlice);
    }
}
