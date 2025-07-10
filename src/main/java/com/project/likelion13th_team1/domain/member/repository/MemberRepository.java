package com.project.likelion13th_team1.domain.member.repository;

import com.project.likelion13th_team1.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("SELECT m " +
            "FROM Member m " +
            "WHERE m.email = :email AND m.deletedAt IS NULL")
    Optional<Member> findByEmailAndNotDeleted(@Param("email") String email);

    Optional<Member> findByEmail(String email);
}
