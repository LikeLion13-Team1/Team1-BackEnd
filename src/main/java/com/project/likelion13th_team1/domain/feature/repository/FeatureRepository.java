package com.project.likelion13th_team1.domain.feature.repository;

import com.project.likelion13th_team1.domain.feature.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
