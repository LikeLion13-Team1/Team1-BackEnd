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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e.scheduledAt " +
            "FROM Event e " +
            "WHERE e.routine = :routine AND e.scheduledAt BETWEEN :today AND :end")
    List<LocalDate> findScheduledDatesByRoutineAndStartBetweenEnd(
            @Param("routine") Routine routine,
            @Param("today") LocalDate today,
            @Param("end") LocalDate end
    );

    // 커서 검색
    @Query("SELECT new com.project.likelion13th_team1.domain.event.dto.EventDto(e) " +
            "FROM Event e " +
            "WHERE e.id > :cursor " +
            "AND e.routine.group.member.email = :email " +
            "AND e.scheduledAt BETWEEN :today AND :end " +
            "ORDER BY e.scheduledAt ASC")
    Slice<EventDto> findAllByEventIdGreaterThanAndScheduledAtBetweenOrderByScheduledAtAsc(
            @Param("email") String email,
            @Param("cursor") Long cursor,
            @Param("today") LocalDate today,
            @Param("end") LocalDate end,
            Pageable pageable
    );

    // 루틴이 삭제 또는 비활성화 됐을 때, 그 자식 루틴 이벤트 일괄 삭제
    @Transactional
    @Modifying
    @Query("DELETE " +
            "FROM Event e " +
            "WHERE e.routine = :routine")
    void deleteByRoutine(@Param("routine") Routine routine);

    @Query("SELECT e " +
            "FROM Event e " +
            "WHERE e.id = :eventId AND e.routine.group.member.email = :email")
    Optional<Event> findByIdAndEmail(@Param("eventId") Long eventId, @Param("email") String email);
}
