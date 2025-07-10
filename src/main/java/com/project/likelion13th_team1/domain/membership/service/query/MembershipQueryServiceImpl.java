package com.project.likelion13th_team1.domain.membership.service.query;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.membership.converter.MembershipConverter;
import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;
import com.project.likelion13th_team1.domain.membership.entity.Membership;
import com.project.likelion13th_team1.domain.membership.exception.MembershipErrorCode;
import com.project.likelion13th_team1.domain.membership.exception.MembershipException;
import com.project.likelion13th_team1.domain.membership.repository.MembershipRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MembershipQueryServiceImpl implements MembershipQueryService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    private final MembershipConverter membershipConverter;

    public MembershipResponseDto.MembershipDetailResponseDto getMembership(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Membership membership = membershipRepository.findByMember(member)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        return membershipConverter.toMembershipGetResponseDto(membership);
    }
}
