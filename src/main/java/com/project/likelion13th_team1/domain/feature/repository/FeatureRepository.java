package com.project.likelion13th_team1.domain.feature.repository;

import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByMember(Member member);
}
