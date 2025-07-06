package com.project.likelion13th_team1.domain.event.entity;

import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.global.entity.RoutineStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "event",
        uniqueConstraints = @UniqueConstraint(columnNames = {"routine_id", "scheduled_at"})
)
public class Event {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 루틴 이벤트가 진행될 시간
    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    // 사용자가 루틴 이벤트 완료를 누른 시간
    @Column(name = "done_at")
    private LocalDateTime doneAt;

    // 루틴 이벤트 상태
    @Column(name = "routine_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoutineStatus routineStatus;

    // 루틴 FK (Event 는 Routine 이라는 설계도로 찍어낸 실제 수행해야할 루틴)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    // updateEvent
    // TODO : 제약조건
    public void updateEvent(LocalDateTime scheduledAt, LocalDateTime doneAt, RoutineStatus routineStatus) {
        if (scheduledAt != null) this.scheduledAt = scheduledAt;
        if (doneAt != null) this.doneAt = doneAt;
        if (routineStatus != null) this.routineStatus = routineStatus;
    }

    // done
    public void doneEvent() {
        this.doneAt = LocalDateTime.now();
    }
}
