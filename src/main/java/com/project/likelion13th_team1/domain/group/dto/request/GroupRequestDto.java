package com.project.likelion13th_team1.domain.group.dto.request;

import jakarta.validation.constraints.NotBlank;

public class GroupRequestDto {
    public record GroupCreateRequestDto(
            @NotBlank String name
    ) {
    }
}
