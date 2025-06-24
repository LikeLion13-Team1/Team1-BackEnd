package com.project.likelion13th_team1.domain.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(name = "username", nullable = false)
    private String username;

    // 이메일
    @Column(name = "email", nullable = false)
    private String email;

    // 비밀번호
    @Column(name = "password", nullable = false)
    private String password;

    // 역할 (일반 사용자, 관리자)
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // 로그인 타입 (로컬, 카카오, 구글 등)
    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // 생성일자
    @Column(name = "createdAt", nullable = false)
    private LocalDateTime createdAt;

    // 수정일자
    @Column(name = "updatedAt", nullable = false)
    private LocalDateTime updatedAt;

    // 루틴 추천을 위한 멤버의 특성
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_feature_id")
    private MemberFeature memberFeature;

    // 생성 시점에 시간 초기화
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // 수정 시점에 시간 초기화
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
