package com.project.likelion13th_team1.global.security.auth.converter;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.global.security.auth.entity.Auth;

public class AuthConverter {

    public static Auth toAuth(String password, Member member) {
        return Auth.builder()
                .member(member)
                .password(password)
                .build();
    }
}
