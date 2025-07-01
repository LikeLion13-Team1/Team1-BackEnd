package com.project.likelion13th_team1.domain.routine.repository;

import com.project.likelion13th_team1.domain.routine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
}
