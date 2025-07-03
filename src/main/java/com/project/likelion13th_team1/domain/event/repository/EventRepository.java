package com.project.likelion13th_team1.domain.event.repository;

import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
}
