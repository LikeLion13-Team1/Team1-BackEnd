package com.project.likelion13th_team1.domain.routine.entity;

import com.project.likelion13th_team1.domain.member.entity.Personality;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "recommended_routine")
public class RecommendedRoutine {
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

    @Column(name = "personality", nullable = false)
    @Enumerated(EnumType.STRING)
    private Personality personality;
}
