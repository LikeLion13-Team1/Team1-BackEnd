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

    @Operation(summary = "그룹 생성", description = "그룹을 생성합니다.<br> 그룹이 생성되면 기본 루틴 세트가 같이 생성됩니다.")
    @PostMapping()
    public CustomResponse<GroupResponseDto.GroupCreateResponseDto> createGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody @Valid GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto
    ) {
        return CustomResponse.onSuccess(groupCommandService.createGroup(customUserDetails.getUsername(), groupCreateRequestDto));
    }

    @Operation(summary = "그룹 단일 조회", description = "그룹 id를 통해 그룹 정보를 조회합니다.")
    @GetMapping("/{groupId}")
    public CustomResponse<GroupResponseDto.GroupDetailResponseDto> getGroup(
            @PathVariable Long groupId
    ) {
        return CustomResponse.onSuccess(groupQueryService.getGroup(groupId));
    }

    @Operation(summary = "그룹 목록 조회 (내 그룹만)", description = "내가 가지고 있는 그룹의 목록을 조회합니다." +
            "<br> 최초의 커서는 0이며, size는 표시할 개수 -> 그러나 현재 그룹 생성제한 3개니까 size 3이면 충분히 다 보입니다.")
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

    @Operation(summary = "그룹 삭제", description = "hard delete" +
            "<br> 그룹을 삭제하면 그룹과 연관된 모든 루틴이 삭제됩니다." +
            "<br> 루틴이 삭제되면 루틴과 연관된 모든 이벤트가 삭제됩니다.")
    @DeleteMapping("/{groupId}")
    public CustomResponse<String> deleteGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long groupId
    ) {
        groupCommandService.deleteGroup(customUserDetails.getUsername(), groupId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "그룹 삭제 완료");
    }

    @Operation(summary = "루틴 추천 그룹 생성",description = "회원 가입 후에 작성한 특성 정보 질문으로 유저의 특성을 생성했다면" +
            "<br> 유저의 특성과 연관된 LAZY, NORMAL, DILIGENT (게으름, 보통, 부지런)이 멤버 객체에 부여됩니다." +
            "<br> 그러고 피그마상으로 마지막에 어떤 그룹에 저장할 건가요? 할때 선택한 그룹 id를 입력하면" +
            "<br> 그룹 내용을 한번 초기화하고, 추천된 루틴 세트가 그룹에 채워집니다.")
    @PostMapping("/groups/{groupId}/recommendation")
    public CustomResponse<String> recommendRoutineGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long groupId
    ) {
        groupCommandService.createRecommendedRoutineGroup(customUserDetails.getUsername(), groupId);
        return CustomResponse.onSuccess(HttpStatus.OK, "추천 루틴 그룹 저장 완료");
    }
}
