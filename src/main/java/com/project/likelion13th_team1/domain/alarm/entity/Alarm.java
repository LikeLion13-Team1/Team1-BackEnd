package com.project.likelion13th_team1.domain.alarm.entity;

import com.project.likelion13th_team1.domain.routine.entity.RoutineEvent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "alarm")
public class Alarm{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "context", nullable = false)
    private String context;

    @Column(name = "activation", nullable = false)
    @Enumerated(EnumType.STRING)
    private Activation activation;

    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineEvent routineEvent;
}
