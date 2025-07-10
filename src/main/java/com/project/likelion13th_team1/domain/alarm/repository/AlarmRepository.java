package com.project.likelion13th_team1.domain.alarm.repository;

import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    // Alarm 조회 페이지.
    @Query("""
    SELECT a
    FROM Alarm a
    WHERE a.routine.id = :routineId
      AND (:cursor IS NULL OR a.id < :cursor)
    ORDER BY a.id DESC
""")
    List<Alarm> findAlarmsByRoutineIdAndCursor(
            @Param("routineId") Long routineEventId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );
}
