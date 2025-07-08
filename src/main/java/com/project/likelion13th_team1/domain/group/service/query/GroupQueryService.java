package com.project.likelion13th_team1.domain.group.service.query;

import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;

public interface GroupQueryService {

    GroupResponseDto.GroupDetailResponseDto getGroup(Long groupId);
}
