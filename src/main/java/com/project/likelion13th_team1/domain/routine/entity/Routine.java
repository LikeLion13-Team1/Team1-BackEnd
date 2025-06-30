package com.project.likelion13th_team1.domain.routine.entity;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.global.entity.BaseEntity;
import com.project.likelion13th_team1.global.feature.entity.Feature;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    // 루틴 타입 (유저가 만든 것인지, 추천받은 것인지)
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    // 반복 주기
    @Column(name = "cycle", nullable = false)
    @Enumerated(EnumType.STRING)
    private Cycle cycle;

    // 루틴 시작 시간
    @Column(name = "startAt", nullable = false)
    private LocalDateTime startAt;

    // 루틴 반복 종료일 (null = 무한반복)
    @Column(name = "endAt")
    private LocalDateTime endAt;

    // 멤버 FK
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 루틴 특성 FK (유저에게 추천해주기 위한?)
    // TODO : 근데 CUSTOM 으로 만드는 경우에는 어떻게 할지?
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id")
    private Feature feature;
}
