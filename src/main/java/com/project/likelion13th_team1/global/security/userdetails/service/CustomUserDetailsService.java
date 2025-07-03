package com.project.likelion13th_team1.global.security.userdetails.service;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    //Username(Email) 로 CustomUserDetail 을 가져오기
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        log.info("[ CustomUserDetailsService ] Email 을 이용하여 User 를 검색합니다.");
        Optional<Member> memberEntity = memberRepository.findByEmail(email);
        if (memberEntity.isPresent()) {
            Member member = memberEntity.get();
            return new CustomUserDetails(member.getEmail(),member.getPassword(), member.getRole());
        }
        throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}