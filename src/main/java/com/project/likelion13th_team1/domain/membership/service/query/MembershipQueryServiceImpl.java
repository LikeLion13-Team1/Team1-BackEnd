package com.project.likelion13th_team1.domain.membership.service.query;

import com.project.likelion13th_team1.domain.member.dto.response.MemberResponseDto;
import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.membership.converter.MembershipConverter;
import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;
import com.project.likelion13th_team1.domain.membership.entity.Membership;
import com.project.likelion13th_team1.domain.membership.repository.MembershipRepository;
import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MembershipQueryServiceImpl implements MembershipQueryService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    private final MembershipConverter membershipConverter;

    public MembershipResponseDto.MembershipGetResponseDto getMembership(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Membership membership = membershipRepository.findByMember(member);

        return membershipConverter.toMembershipGetResponseDto(membership);
    }
}
