package com.project.likelion13th_team1.domain.routine.repository;

import com.project.likelion13th_team1.domain.group.entity.Group;
import com.project.likelion13th_team1.domain.routine.dto.RoutineDto;
import com.project.likelion13th_team1.domain.routine.entity.Routine;
import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.MetadataDefinition;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Long> {

    // 멤버 이메일, 루틴 아이디로 루틴 찾기
    @Query("SELECT r " +
            "FROM Routine r " +
            "WHERE r.group.member.email = :email AND r.id = :routineId")
    Optional<Routine> findByMemberEmailAndRoutineId(@Param("email") String email, @Param("routineId") Long routineId);

    // 커서 검색(유저 기준)
    @Query("SELECT new com.project.likelion13th_team1.domain.routine.dto.RoutineDto(r) " +
            "FROM Routine r " +
            "WHERE r.id > :cursor AND r.group.member.email = :email " +
            "ORDER BY r.id ASC")
    Slice<RoutineDto> findAllByRoutineIdLessThanOrderByRoutineIdASC(
            @Param("email") String email,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    // 커서 검색(그룹 기준)
    @Query("SELECT new com.project.likelion13th_team1.domain.routine.dto.RoutineDto(r) " +
            "FROM Routine r " +
            "WHERE r.id > :cursor AND r.group.member.email = :email AND r.group.id = :groupId " +
            "ORDER BY r.id ASC")
    Slice<RoutineDto> findAllByGroupIdLessThanOrderByRoutineIdASC(
            @Param("email") String email,
            @Param("groupId") Long groupId,
            @Param("cursor") Long cursor,
            Pageable pageable
    );

    @Query("SELECT r " +
            "FROM Routine r " +
            "WHERE r.isActive = true")
    List<Routine> findAllActiveRoutines();

    @Transactional
    @Modifying
    @Query("DELETE " +
            "FROM Routine r " +
            "WHERE r.group = :group")
    void deleteByGroup(@Param("group") Group group);
}
