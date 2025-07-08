package com.project.likelion13th_team1.domain.group.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/groups")
@Tag(name = "Group", description = "그룹 관련 API")
public class GroupController {
}
