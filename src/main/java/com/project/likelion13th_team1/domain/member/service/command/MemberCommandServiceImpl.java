package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.group.service.command.GroupCommandService;
import com.project.likelion13th_team1.domain.member.converter.MemberConverter;
import com.project.likelion13th_team1.domain.member.converter.SocialLoginConverter;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.entity.Personality;
import com.project.likelion13th_team1.domain.member.entity.SocialLogin;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.member.repository.SocialLoginRepository;
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
    private final SocialLoginRepository socialLoginRepository;
    private final GroupCommandService groupCommandService;

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

        // 그룹 생성
        groupCommandService.initGroup(member);
    }

    @Override
    public void createSocialMember(MemberRequestDto.MemberSocialCreateRequestDto dto) {

        Member member = MemberConverter.toMember(dto);
        SocialLogin socialLogin = SocialLoginConverter.toSocialLogin(dto, member);
        try {
            memberRepository.save(member);
            socialLoginRepository.save(socialLogin);
        } catch (DataIntegrityViolationException e) {
            throw new MemberException(MemberErrorCode.MEMBER_EMAIL_DUPLICATE);
        }
        // 그룹 생성
        groupCommandService.initGroup(member);
    }

    @Override
    public void updateMember(String email, MemberRequestDto.MemberUpdateRequestDto dto) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        member.updateUsername(dto);
    }

    @Override
    public void deleteMember(String email) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

//        // soft delete
//        member.delete();
        memberRepository.delete(member);
    }

    @Override
    public void updatePersonality(Member member, Personality personality) {
        member.updatePersonality(personality);
    }
}
