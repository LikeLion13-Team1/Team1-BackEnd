package com.project.likelion13th_team1.domain.feature.entity;


import com.project.likelion13th_team1.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "feature")
public class Feature {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO : enum을 숫자로 저장할 수 있는 방법에 대해서 생각해보기 (조인이 더 빠르대요!)

    @Column(name = "Q1")
    private Integer q1;

    @Column(name = "Q2")
    private Integer q2;

    @Column(name = "Q3")
    private Integer q3;

    @Column(name = "Q4")
    private Integer q4;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    public void updateQ1(Integer q1) {
        this.q1 = q1;
    }

    public void updateQ2(Integer q2) {
        this.q2 = q2;
    }

    public void updateQ3(Integer q3) {
        this.q3 = q3;
    }

    public void updateQ4(Integer q4) {
        this.q4 = q4;
    }
}
