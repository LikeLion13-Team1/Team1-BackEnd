package com.project.likelion13th_team1.domain.member.repository;

import com.project.likelion13th_team1.domain.member.entity.SocialLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialLoginRepository extends JpaRepository<SocialLogin, Long> {
}
