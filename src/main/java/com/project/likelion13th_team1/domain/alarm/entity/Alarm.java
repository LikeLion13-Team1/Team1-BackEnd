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

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 알람 내용
    @Column(name = "context", nullable = false)
    private String context;

    // 할거냐 말거냐
    @Column(name = "activation", nullable = false)
    @Enumerated(EnumType.STRING)
    private Activation activation;

    // 알람을 할 시간
    @Column(name = "time", nullable = false)
    private LocalDateTime time;

    // 알람을 할 실제 루틴 정보
    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineEvent routineEvent;

    public void setContext(String context) {
        this.context = context;
    }

    public void setActivation(Activation activation) {
        this.activation = activation;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
