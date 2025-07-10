package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.converter.MemberConverter;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.feature.repository.FeatureRepository;
import com.project.likelion13th_team1.global.security.auth.converter.AuthConverter;
import com.project.likelion13th_team1.global.security.auth.entity.Auth;
import com.project.likelion13th_team1.global.security.auth.repository.AuthRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final AuthRepository authRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createLocalMember(MemberRequestDto.MemberCreateRequestDto dto) {
        // TODO : 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto);
        Auth auth = AuthConverter.toAuth(encodedPassword, member);
        try {
            memberRepository.save(member);
            authRepository.save(auth);
        } catch (DataIntegrityViolationException e) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATE);
        }
    }

    @Override
    public void updateMember(String email, MemberRequestDto.MemberUpdateRequestDto dto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateUsername(dto);
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // soft delete
        member.delete();
    }
}
