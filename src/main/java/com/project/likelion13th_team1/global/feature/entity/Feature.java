package com.project.likelion13th_team1.global.feature.entity;


import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
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

    public void updateFeature(FeatureRequestDto.FeatureUpdateRequestDto dto) {
        if (dto.messyHouse() != null) this.messyHouse = dto.messyHouse();
        if (dto.cleaningStyle() != null) this.cleaningStyle = dto.cleaningStyle();
        if (dto.cleanHouse() != null) this.cleanHouse = dto.cleanHouse();
        if (dto.routineStyle() != null) this.routineStyle = dto.routineStyle();
    }
}
