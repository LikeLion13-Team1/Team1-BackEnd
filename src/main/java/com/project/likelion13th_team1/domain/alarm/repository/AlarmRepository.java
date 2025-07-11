package com.project.likelion13th_team1.domain.alarm.repository;

import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.event.entity.Event;
import com.project.likelion13th_team1.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

    // Event 객체로 Alarm 탐색
    Optional<Alarm> findByEvent(Event event);

    // Alarm 조회 페이지.
    @Query("""
    SELECT a
    FROM Alarm a
    WHERE a.event.id = :eventId
      AND (:cursor IS NULL OR a.id < :cursor)
    ORDER BY a.id DESC
""")
    List<Alarm> findAlarmsByEventIdAndCursor(
            @Param("eventId") Long eventId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    @Query("""
    SELECT a FROM Alarm a
    JOIN FETCH a.event e
    JOIN FETCH e.routine r
    JOIN FETCH r.group g
    JOIN FETCH g.member m
    WHERE m.email = :memberEmail
    """)
    List<Alarm> findAllByMemberEmail(@Param("memberEmail") String memberEmail);

    @Query("""
    SELECT a FROM Alarm a
    JOIN a.event e
    JOIN e.routine r
    JOIN r.group g
    WHERE g.member = :member
""")
    List<Alarm> findAllByEvent_Routine_Group_Member(@Param("member") Member member);

}
