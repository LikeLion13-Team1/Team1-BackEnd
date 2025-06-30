package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.converter.MemberConverter;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public MemberResponseDto.MemberCreateResponseDto createMember(MemberRequestDto.MemberCreateRequestDto dto) {
        // TODO : 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto, encodedPassword);
        memberRepository.save(member);
        return MemberConverter.toMemberResponseDto(member);
    }
}
