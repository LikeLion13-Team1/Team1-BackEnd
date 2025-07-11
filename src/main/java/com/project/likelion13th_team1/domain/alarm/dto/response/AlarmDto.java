package com.project.likelion13th_team1.domain.alarm.dto.response;

import com.project.likelion13th_team1.domain.alarm.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmDto {
    private Long id;
    private String context;
    private String activation;
    private LocalDateTime time;
    private String eventTitle;

    public static AlarmDto from(Alarm alarm) {
        return AlarmDto.builder()
                .id(alarm.getId())
                .context(alarm.getContext())
                .activation(alarm.getActivation().name())
                .time(alarm.getTime())
                .build();
    }
}

