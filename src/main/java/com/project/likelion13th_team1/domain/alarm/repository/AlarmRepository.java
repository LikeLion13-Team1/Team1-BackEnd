package com.project.likelion13th_team1.domain.alarm.repository;

import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}
