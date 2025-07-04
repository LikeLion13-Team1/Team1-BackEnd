package com.project.likelion13th_team1.domain.routine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.global.entity.BaseEntity;
import com.project.likelion13th_team1.global.entity.RoutineStatus;
import com.project.likelion13th_team1.global.feature.entity.Feature;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "routine")
public class Routine extends BaseEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 루틴 이름
    @Column(name = "name", nullable = false)
    private String name;

    // 루틴 설명 (이게 뭐하는 루틴인지)
    @Column(name = "description")
    private String description;

    // 루틴 상태 (진행 중인지 끝났는지)
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RoutineStatus routineStatus;

    // 루틴 타입 (유저가 만든 것인지, 추천받은 것인지)
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    // 반복 주기
    @Column(name = "cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    // 루틴 시작 시간
    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    // 루틴 반복 종료일 (null = 무한반복)
    @Column(name = "end_at")
    private LocalDateTime endAt;

    // 루틴 삭제 soft
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 멤버 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 루틴 특성 FK (유저에게 추천해주기 위한?)
    // TODO : 근데 CUSTOM 으로 만드는 경우에는 어떻게 할지?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;

    // TODO : DTO 제약 조건이 필요하다
    public void updateRoutine(RoutineRequestDto.RoutineUpdateRequestDto dto) {
        if (dto.name() != null) this.name = dto.name();
        if (dto.description() != null) this.description = dto.description();
        if (dto.routineStatus() != null) this.routineStatus = dto.routineStatus();
        if (dto.cycle() != null) this.cycle = dto.cycle();
        if (dto.startAt() != null) this.startAt = dto.startAt();
        if (dto.endAt() != null) this.endAt = dto.endAt();
    }

    public void delete(Routine routine) {
        this.deletedAt = LocalDateTime.now();
    }
}
