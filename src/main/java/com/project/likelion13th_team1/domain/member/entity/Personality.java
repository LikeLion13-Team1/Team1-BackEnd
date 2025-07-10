package com.project.likelion13th_team1.domain.member.entity;

import java.util.Arrays;

public enum Personality {
    LAZY(4, 6),
    NORMAL(7, 9),
    DILIGENT(10, 12);

    private final int min;
    private final int max;

    Personality(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public static Personality fromScore(int score) {
        return Arrays.stream(values())
                .filter(p -> score >= p.min && score <= p.max)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid score: " + score));
    }
}
