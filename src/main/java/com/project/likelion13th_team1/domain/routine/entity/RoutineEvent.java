package com.project.likelion13th_team1.domain.routine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "routine_event")
public class RoutineEvent {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이 루틴이 진행될 시간
    @Column(name = "scheduledAt", nullable = false)
    private LocalDateTime scheduledAt;

    // 사용자가 루틴 완료를 누른 시간
    @Column(name = "doneAt")
    private LocalDateTime doneAt;

    // 루틴 상태
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    // 루틴 FK (Routine Event 는 Routine 이라는 설계도로 찍어낸 실제 수행해야할 루틴)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routine_id")
    private Routine routine;
}
