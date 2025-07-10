package com.project.likelion13th_team1.domain.routine.repository;

import com.project.likelion13th_team1.domain.member.entity.Personality;
import com.project.likelion13th_team1.domain.routine.entity.RecommendedRoutine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendedRoutineRepository extends JpaRepository<RecommendedRoutine, Long> {
    List<RecommendedRoutine> findByPersonality(Personality personality);
}
