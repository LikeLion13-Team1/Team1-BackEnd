package com.project.likelion13th_team1.domain.group.controller;

import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;
import com.project.likelion13th_team1.domain.group.service.command.GroupCommandService;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
@Tag(name = "Group", description = "그룹 관련 API")
public class GroupController {

    private final GroupCommandService groupCommandService;

    @Operation(summary = "그룹 생성")
    @PostMapping()
    public CustomResponse<GroupResponseDto.GroupCreateResponseDto> createGroup(
            @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestBody GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto
    ) {
        return CustomResponse.onSuccess(groupCommandService.createGroup(customUserDetails.getUsername(), groupCreateRequestDto));
    }
}
