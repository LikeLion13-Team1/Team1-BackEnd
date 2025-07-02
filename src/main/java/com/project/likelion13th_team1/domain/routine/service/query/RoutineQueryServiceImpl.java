package com.project.likelion13th_team1.domain.routine.service.query;

import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
}
