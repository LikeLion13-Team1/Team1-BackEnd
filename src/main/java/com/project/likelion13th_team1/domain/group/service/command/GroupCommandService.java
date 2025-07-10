package com.project.likelion13th_team1.domain.group.service.command;

import com.project.likelion13th_team1.domain.group.dto.request.GroupRequestDto;
import com.project.likelion13th_team1.domain.group.dto.response.GroupResponseDto;

public interface GroupCommandService {

    GroupResponseDto.GroupCreateResponseDto createGroup(String email, GroupRequestDto.GroupCreateRequestDto groupCreateRequestDto);

    void updateGroup(String email, Long GroupId, GroupRequestDto.GroupUpdateRequestDto groupUpdateRequestDto);

    void deleteGroup(String email, Long groupId);

    void createRecommendedRoutineGroup(String email, Long groupId);

}
