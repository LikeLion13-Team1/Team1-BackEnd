package com.project.likelion13th_team1.global.security.auth.repository;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.global.security.auth.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByMember(Member member);
}
