package com.project.likelion13th_team1.domain.event.repository;

import com.project.likelion13th_team1.domain.event.dto.EventDto;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e.scheduledAt " +
            "FROM Event e " +
            "WHERE e.routine = :routine AND e.scheduledAt BETWEEN :start AND :end")
    List<LocalDateTime> findScheduledDatesByRoutineAndStartBetweenEnd(
            @Param("routine") Routine routine,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    // 커서 검색
    @Query("SELECT new com.project.likelion13th_team1.domain.event.dto.EventDto(e) " +
            "FROM Event e " +
            "WHERE e.id > :cursor " +
            "AND e.routine.member.email = :email " +
            "AND e.scheduledAt BETWEEN :start AND :end " +
            "ORDER BY e.scheduledAt ASC")
    Slice<EventDto> findAllByEventIdGreaterThanAndScheduledAtBetweenOrderByScheduledAtAsc(
            @Param("email") String email,
            @Param("cursor") Long cursor,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end,
            Pageable pageable
    );

    // 루틴이 삭제됐을 때, 그 자식 루틴 이벤트 일괄 삭제
    @Transactional
    @Modifying
    @Query("DELETE " +
            "FROM Event e " +
            "WHERE e.routine = :routine")
    void deleteByRoutine(@Param("routine") Routine routine);
}
