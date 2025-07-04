package com.project.likelion13th_team1.domain.member.service.command;

import com.project.likelion13th_team1.domain.member.converter.MemberConverter;
import com.project.likelion13th_team1.domain.member.dto.request.MemberRequestDto;
import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.global.feature.converter.FeatureConverter;
import com.project.likelion13th_team1.global.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.global.feature.dto.response.FeatureResponseDto;
import com.project.likelion13th_team1.global.feature.entity.Feature;
import com.project.likelion13th_team1.global.feature.entity.FeatureType;
import com.project.likelion13th_team1.global.feature.exception.FeatureErrorCode;
import com.project.likelion13th_team1.global.feature.exception.FeatureException;
import com.project.likelion13th_team1.global.feature.repository.FeatureRepository;
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
    private final FeatureRepository featureRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createMember(MemberRequestDto.MemberCreateRequestDto dto) {
        // TODO : 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.password());
        Member member = MemberConverter.toMember(dto, encodedPassword);

        try {
            memberRepository.save(member);
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

    @Override
    public void createFeature(String email, FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // TODO : 중복저장 방지
        Feature feature = FeatureConverter.toFeature(featureCreateRequestDto, FeatureType.MEMBER);
        featureRepository.save(feature);

        member.linkFeature(feature);
    }

    @Override
    public void updateFeature(String email, FeatureRequestDto.FeatureUpdateRequestDto featureUpdateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Feature feature = member.getFeature();
        if(feature == null) {
            throw new FeatureException(FeatureErrorCode.FEATURE_NOT_FOUND);
        }
        feature.updateFeature(featureUpdateRequestDto);
    }

    @Override
    public FeatureResponseDto.FeatureDetailResponseDto getFeature(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Feature feature = member.getFeature();
        if(feature == null) {
            throw new FeatureException(FeatureErrorCode.FEATURE_NOT_FOUND);
        }
        return FeatureConverter.toFeatureDetailResponseDto(feature);
    }
}
