package com.project.likelion13th_team1.domain.routine.entity;

public enum Cycle {
    // 반복 없음, 하루, 일주일 ......
    NO(0),
    DAY(1),
    WEEK(7),
    MONTH(30),
    YEAR(365),
    ;

    private final int days;

    Cycle(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
