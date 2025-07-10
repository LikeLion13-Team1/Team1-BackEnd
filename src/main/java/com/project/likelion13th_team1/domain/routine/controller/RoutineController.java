package com.project.likelion13th_team1.domain.routine.controller;

import com.project.likelion13th_team1.domain.routine.dto.request.RoutineRequestDto;
import com.project.likelion13th_team1.domain.routine.dto.response.RoutineResponseDto;
import com.project.likelion13th_team1.domain.routine.service.command.RoutineCommandService;
import com.project.likelion13th_team1.domain.routine.service.query.RoutineQueryService;
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
@RequestMapping("/api/v1")
@Tag(name = "Routine", description = "루틴 관련 API")
public class RoutineController {

    private final RoutineCommandService routineCommandService;
    private final RoutineQueryService routineQueryService;

    @Operation(summary = "루틴 생성",
            description = "루틴 이름, 루틴 설명, 시작 시간은 빈칸일 수 없다." +
            "<br>status는 루틴의 상태를 말하며, PROCESSING, SUCCESS가 있다. " +
            "<br> cycle은 주기로 NO, DAY, WEEK, MONTH, YEAR이 있다. " +
            "<br> cycle이 no인 경우에는 endAt은 null" +
            "<br> 1년을 넘는 범위로 설정하면, 이벤트는 현재 시간으로부터 1년까지만 생성되고 날짜가 지남에 따라 매일 자정에 자동 추가 생성된다.")
    @PostMapping("/group/{groupId}/routines")
    public CustomResponse<RoutineResponseDto.RoutineCreateResponseDto> createRoutine(
            @PathVariable Long groupId,
            @RequestBody @Valid RoutineRequestDto.RoutineCreateRequestDto routineCreateRequestDto
    ) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, routineCommandService.createRoutine(groupId, routineCreateRequestDto));
    }

    @Operation(summary = "루틴 수정", description = "루틴을 수정한다. 생성과 마찬가지로 이름, 설명, 시작 시간이 빈칸일 수 없다")
    @PatchMapping("routines/{routineId}")
    public CustomResponse<RoutineResponseDto.RoutineUpdateResponseDto> updateRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId,
            @RequestBody RoutineRequestDto.RoutineUpdateRequestDto routineUpdateRequestDto

    ) {
        return CustomResponse.onSuccess(routineCommandService.updateRoutine(customUserDetails.getUsername(), routineId, routineUpdateRequestDto));
    }

    @Operation(summary = "루틴 삭제", description = "루틴을 삭제한다. 해당 루틴을 통해 생성된 이벤트들도 함께 삭제된다.")
    @DeleteMapping("routines/{routineId}")
    public CustomResponse<String> deleteRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId
    ) {
        routineCommandService.deleteRoutine(customUserDetails.getUsername(), routineId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "루틴 삭제 완료");
    }

    @Operation(summary = "루틴 단일 조회", description = "루틴 아이디로 루틴 1개를 검색한다.")
    @GetMapping("routines/{routineId}")
    public CustomResponse<RoutineResponseDto.RoutineDetailResponseDto> getRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId
    ) {
        return CustomResponse.onSuccess(routineQueryService.getRoutine(customUserDetails.getUsername(), routineId));
    }

    @Operation(summary = "루틴 목록 커서 조회 (유저의 모든 루틴)", description = "cursor은 커서 위치로 맨 초기에는 0을 입력한다 <br>size는 한번에 나타낼 객체의 개수이다.<br>hasNextCursor가 true라면 뒤에 내용이 더 있다는 의미이므로 다음 커서를 nextCursor값으로 입력하면 계속해서 객체가 출력된다.")
    @GetMapping("/routines/my")
    public CustomResponse<RoutineResponseDto.RoutineCursorResponseDto> getMyRoutineCursor(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam Long cursor,
            @RequestParam Integer size
    ) {
        return CustomResponse.onSuccess(routineQueryService.getMyRoutineCursor(customUserDetails.getUsername(), cursor, size));
    }

    @Operation(summary = "루틴 목록 커서 조회( 그룹의 모든 루틴)", description = "cursor은 커서 위치로 맨 초기에는 0을 입력한다 <br>size는 한번에 나타낼 객체의 개수이다.<br>hasNextCursor가 true라면 뒤에 내용이 더 있다는 의미이므로 다음 커서를 nextCursor값으로 입력하면 계속해서 객체가 출력된다.")
    @GetMapping("/groups/{groupId}/routines/my")
    public CustomResponse<RoutineResponseDto.RoutineCursorResponseDto> getMyGroupRoutineCursor(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long groupId,
            @RequestParam Long cursor,
            @RequestParam Integer size
    ) {
        return CustomResponse.onSuccess(routineQueryService.getMyGroupRoutineCursor(customUserDetails.getUsername(), groupId, cursor, size));
    }

    @Operation(summary = "루틴 활성화", description = "루틴의 isActive를 true로 바꾸고, 이벤트를 생성한다.")
    @PatchMapping("/routines/{routineId}/activate")
    public CustomResponse<String> activateRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId
    ) {
        routineCommandService.activateRoutine(customUserDetails.getUsername(), routineId);
        return CustomResponse.onSuccess(HttpStatus.OK, "루틴 활성화 완료");
    }

    @Operation(summary = "루틴 비활성화", description = "루틴의 isActive를 false로 바꾸고, 이벤트를 삭제한다.")
    @PatchMapping("/routines/{routineId}/inactivate")
    public CustomResponse<String> inactivateRoutine(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @PathVariable Long routineId
    ) {
        routineCommandService.inactivateRoutine(customUserDetails.getUsername(), routineId);
        return CustomResponse.onSuccess(HttpStatus.OK, "루틴비활성화 완료");
    }

    @Operation(summary = "루틴 추천", description = "멤버와 루틴의 특성 정보를 매칭시켜 루틴을 추천한다")
    @PostMapping("/routines/recommendation")
    public CustomResponse<?> recommendRoutine() {
        return CustomResponse.onFailure("500", "구상을 해봐야 해요", null);
    }
}
