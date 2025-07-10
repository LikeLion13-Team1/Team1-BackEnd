package com.project.likelion13th_team1.domain.feature.service.query;

import com.project.likelion13th_team1.domain.feature.repository.FeatureRepository;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.feature.converter.FeatureConverter;
import com.project.likelion13th_team1.domain.feature.dto.response.FeatureResponseDto;
import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.feature.exception.FeatureErrorCode;
import com.project.likelion13th_team1.domain.feature.exception.FeatureException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeatureQueryServiceImpl implements FeatureQueryService {

    private final MemberRepository memberRepository;
    private final FeatureRepository featureRepository;

    @Override
    public FeatureResponseDto.FeatureDetailResponseDto getFeature(String email) {
        Member member = memberRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        Feature feature = featureRepository.findByMember(member)
                .orElseThrow(() -> new FeatureException(FeatureErrorCode.FEATURE_NOT_FOUND));
        return FeatureConverter.toFeatureDetailResponseDto(feature, member.getPersonality());
    }
}
