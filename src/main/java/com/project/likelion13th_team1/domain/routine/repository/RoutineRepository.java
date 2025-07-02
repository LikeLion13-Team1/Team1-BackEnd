package com.project.likelion13th_team1.domain.routine.repository;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    // 멤버 이메일, 루틴 아이디로 루틴 찾기
    @Query("SELECT r " +
            "FROM Routine r " +
            "WHERE r.member.email = :email AND r.id = :routineId")
    Optional<Routine> findByMemberEmailAndRoutineId(String email, Long routineId);

    // 커서 검색
    @Query("SELECT new com.project.likelion13th_team1.domain.routine.dto.RoutineDto(r) " +
            "FROM Routine r " +
            "WHERE r.id < :cursor AND r.member.email = :email " +
            "ORDER BY r.id DESC")
    Slice<RoutineDto> findAllByRoutineIdLessThanOrderByRoutineIdDesc(
            @Param("email") String email,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
