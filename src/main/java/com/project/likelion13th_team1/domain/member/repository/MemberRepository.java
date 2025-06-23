package com.project.likelion13th_team1.domain.member.repository;

import com.project.likelion13th_team1.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
