package com.project.likelion13th_team1.domain.membership.repository;

import com.project.likelion13th_team1.domain.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {
}
