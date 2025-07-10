package com.project.likelion13th_team1.domain.routine.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "example_routine")
public class ExampleRoutine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 루틴 이름
    @Column(name = "name", nullable = false)
    private String name;

    // 루틴 설명 (이게 뭐하는 루틴인지)
    @Column(name = "description")
    private String description;

    // 사용 여부
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    // 반복 주기
    @Column(name = "cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

//    // 루틴 시작 시간
//    @Column(name = "start_at", nullable = false)
//    private LocalDate startAt;
//
//    // 루틴 반복 종료일 (null = 무한반복)
//    @Column(name = "end_at")
//    private LocalDate endAt;
}
