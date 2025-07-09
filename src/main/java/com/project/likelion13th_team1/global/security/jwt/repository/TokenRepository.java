package com.project.likelion13th_team1.global.security.jwt.repository;

import com.project.likelion13th_team1.global.security.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByEmail(String email);
}
