package com.project.likelion13th_team1.domain.alarm.entity;

public enum Activation {
    // 할거냐, 말거냐
    Y, N;

    // 토글 메서드
    public Activation toggle() {
        return this == Y ? N : Y;
    }
}
