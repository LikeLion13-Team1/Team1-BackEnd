package com.project.likelion13th_team1.domain.member.entity;

import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.global.entity.BaseEntity;
import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.global.security.auth.entity.Auth;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member extends BaseEntity {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 이름
    @Column(name = "username", nullable = false)
    private String username;

    // 이메일
    @Column(name = "email", nullable = false, unique = true)
    private String email;

//    // 비밀번호
//    @Column(name = "password", nullable = false)
//    private String password;

    // 프로필 사진
    @Column(name = "profile_image")
    private String profileImage;

    // 역할 (일반 사용자, 관리자)
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    // 로그인 타입 (로컬, 카카오, 구글 등)
    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "personality")
    @Enumerated(EnumType.STRING)
    private Personality personality;

    // soft delete 여부
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // 루틴 추천을 위한 멤버의 특성
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "feature_id", unique = true)
    private Feature feature;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Auth auth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Group> group;

    // feature 생성 후 연결 메서드
    // TODO : 이 연결 메서드를 제네릭으로 한다면!! Routine에서 재활용할 수 있지 않을까요
    public void linkFeature(Feature feature) {
        this.feature = feature;
    }

    // member update 메서드
    public void updateUsername(MemberRequestDto.MemberUpdateRequestDto dto) {
        this.username = dto.username();
    }

    // member soft delete 메서드
    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    // member personality update
    public void updatePersonality(Personality personality) {
        this.personality = personality;
    }
}
