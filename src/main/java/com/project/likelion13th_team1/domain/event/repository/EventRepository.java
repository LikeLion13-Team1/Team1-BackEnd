package com.project.likelion13th_team1.domain.event.repository;

import com.project.likelion13th_team1.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
