package com.project.likelion13th_team1.domain.feature.service.command;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.feature.converter.FeatureConverter;
import com.project.likelion13th_team1.domain.feature.dto.request.FeatureRequestDto;
import com.project.likelion13th_team1.domain.feature.entity.Feature;
import com.project.likelion13th_team1.domain.feature.exception.FeatureErrorCode;
import com.project.likelion13th_team1.domain.feature.exception.FeatureException;
import com.project.likelion13th_team1.domain.feature.repository.FeatureRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class FeatureCommandServiceImpl implements FeatureCommandService {

    private final MemberRepository memberRepository;
    private final FeatureRepository featureRepository;

    @Override
    public void createFeature(String email, FeatureRequestDto.FeatureCreateRequestDto featureCreateRequestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        // TODO : 중복저장 방지
        Feature feature = FeatureConverter.toFeature(featureCreateRequestDto);
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

        if (featureUpdateRequestDto.q1() != null) feature.updateQ1(featureUpdateRequestDto.q1());
        if (featureUpdateRequestDto.q2() != null) feature.updateQ2(featureUpdateRequestDto.q2());
        if (featureUpdateRequestDto.q3() != null) feature.updateQ3(featureUpdateRequestDto.q3());
        if (featureUpdateRequestDto.q4() != null) feature.updateQ4(featureUpdateRequestDto.q4());


    }
}
