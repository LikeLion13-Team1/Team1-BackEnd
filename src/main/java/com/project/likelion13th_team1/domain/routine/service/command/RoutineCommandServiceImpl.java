package com.project.likelion13th_team1.domain.routine.service.command;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.routine.converter.RoutineConverter;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.domain.routine.exception.RoutineErrorCode;
import com.project.likelion13th_team1.domain.routine.exception.RoutineException;
import com.project.likelion13th_team1.domain.routine.repository.RoutineRepository;
import com.project.likelion13th_team1.global.apiPayload.code.GeneralErrorCode;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RoutineCommandServiceImpl implements RoutineCommandService {

    private final MemberRepository memberRepository;
    private final RoutineRepository routineRepository;

    @Override
    public RoutineResponseDto.RoutineCreateResponseDto createRoutine(String email, RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = RoutineConverter.toRoutine(routineCreateRequestDto, member);
        routineRepository.save(routine);

        return RoutineConverter.toRoutineCreateResponseDto(routine);
    }

    @Override
    public RoutineResponseDto.RoutineUpdateResponseDto updateRoutine(String email, Long routineId, RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto) {
        // TODO : update랑 delete 공통 부분을 private method로 묶기?
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        routine.updateRoutine(routineUpdateRequestDto);
        return RoutineConverter.toRoutineUpdateResponseDto(routine);
    }

    // TODO : 스케줄러 구현하기
    @Override
    public void deleteRoutine(String email, Long routineId) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new RoutineException(RoutineErrorCode.ROUTINE_NOT_FOUND));

        if (routine.getMember() != member) {
            throw new CustomException(GeneralErrorCode.FORBIDDEN_403);
        }

        routine.delete(routine);
    }
}
