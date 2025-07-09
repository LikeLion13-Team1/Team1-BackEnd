package com.project.likelion13th_team1.domain.membership.entity;

import com.project.likelion13th_team1.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "membership")
public class Membership {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 멤버쉽 상태
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipStatus status;

    // 시작 시간
    @Column(name = "joinedAt", nullable = false)
    private LocalDateTime joinedAt;

    // 만료 시간 (처음 가입시에는 최대 값으로 설정하면 될듯?)
    @Column(name = "expiredAt", nullable = false)
    private LocalDateTime expiredAt;

    // 멤버 정보 FK
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }
}
