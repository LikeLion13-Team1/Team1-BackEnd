package com.project.likelion13th_team1.domain.group.repository;

import com.project.likelion13th_team1.domain.group.dto.GroupDto;
import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.member.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long> {
    // 커서 검색
    @Query("SELECT new com.project.likelion13th_team1.domain.group.dto.GroupDto(g) " +
            "FROM Group g " +
            "WHERE g.id > :cursor AND g.member.email = :email " +
            "ORDER BY g.id ASC")
    Slice<GroupDto> findAllByGroupIdGreaterThanOrderByGroupIdASC(
            @Param("email") String email,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    Long countByMember(Member member);
}
