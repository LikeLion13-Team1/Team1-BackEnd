package com.project.likelion13th_team1.domain.event.entity;

import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import com.project.likelion13th_team1.global.entity.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private LocalDate scheduledAt;

    // 사용자가 루틴 이벤트 완료를 누른 시간
    @Column(name = "done_at")
    private LocalDate doneAt;

    // 루틴 이벤트 상태
    @Column(name = "routine_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // 루틴 FK (Event 는 Routine 이라는 설계도로 찍어낸 실제 수행해야할 루틴)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;

    // Alarm과 양방향 매핑 관계 설정, Event가 삭제된다면 Alarm도 삭제
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarms;


    // updateEvent
    // TODO : 제약조건
    public void updateEvent(LocalDate scheduledAt, LocalDate doneAt, Status status) {
        if (scheduledAt != null) this.scheduledAt = scheduledAt;
        if (doneAt != null) this.doneAt = doneAt;
        if (status != null) this.status = status;
    }

    // done
    public void doneEvent() {
        this.doneAt = LocalDate.now();
        this.status = Status.SUCCESS;
    }

    // undone
    public void undoneEvent() {
        this.doneAt = null;
        this.status = Status.PROCESSING;
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}
