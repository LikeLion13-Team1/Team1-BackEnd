package com.project.likelion13th_team1.domain.group.controller;

import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.service.command.GroupCommandService;
import com.project.likelion13th_team1.domain.group.service.query.GroupQueryService;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
@Tag(name = "Group", description = "그룹 관련 API")
public class GroupController {

    private final GroupCommandService groupCommandService;
    private final GroupQueryService groupQueryService;

    @Operation(summary = "그룹 생성")
    @PostMapping()
    public CustomResponse<GroupResponseDto.GroupCreateResponseDto> createGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto
    ) {
        return CustomResponse.onSuccess(groupCommandService.createGroup(customUserDetails.getUsername(), groupCreateRequestDto));
    }

    @Operation(summary = "그룹 단일 조회")
    @GetMapping("/{groupId}")
    public CustomResponse<GroupResponseDto.GroupDetailResponseDto> getGroup(
            @PathVariable Long groupId
    ) {
        return CustomResponse.onSuccess(groupQueryService.getGroup(groupId));
    }

    @Operation(summary = "그룹 목록 조회 (내 그룹만)")
    @GetMapping("/my")
    public CustomResponse<GroupResponseDto.GroupCursorResponseDto> getGroupCursor(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long cursor,
            @RequestParam Integer size
    ) {
        return CustomResponse.onSuccess(groupQueryService.getGroupCursor(customUserDetails.getUsername(), cursor, size));
    }

    @Operation(summary = "그룹 수정", description = "이름은 빈칸일 수 없음")
    @PatchMapping("/{groupId}")
    public CustomResponse<String> updateGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long groupId,
            @RequestBody @Valid GroupRequestDto.GroupUpdateRequestDto groupUpdateRequestDto
    ) {
        groupCommandService.updateGroup(customUserDetails.getUsername(), groupId, groupUpdateRequestDto);
        return CustomResponse.onSuccess(HttpStatus.OK, "그룹 수정 완료");
    }
}
