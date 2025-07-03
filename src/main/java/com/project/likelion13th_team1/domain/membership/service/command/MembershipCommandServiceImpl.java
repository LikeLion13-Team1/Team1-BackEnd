package com.project.likelion13th_team1.domain.membership.service.command;

import com.project.likelion13th_team1.domain.member.entity.Member;
import com.project.likelion13th_team1.domain.member.exception.MemberErrorCode;
import com.project.likelion13th_team1.domain.member.exception.MemberException;
import com.project.likelion13th_team1.domain.member.repository.MemberRepository;
import com.project.likelion13th_team1.domain.membership.converter.MembershipConverter;
import com.project.likelion13th_team1.domain.membership.dto.response.MembershipResponseDto;
import com.project.likelion13th_team1.domain.membership.entity.Membership;
import com.project.likelion13th_team1.domain.membership.entity.MembershipStatus;
import com.project.likelion13th_team1.domain.membership.exception.MembershipErrorCode;
import com.project.likelion13th_team1.domain.membership.exception.MembershipException;
import com.project.likelion13th_team1.domain.membership.repository.MembershipRepository;
import com.project.likelion13th_team1.global.apiPayload.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MembershipCommandServiceImpl implements MembershipCommandService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipConverter membershipConverter;

    // 멤버십 가입
    public MembershipResponseDto.MembershipJoinResponseDto joinMembership(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Membership membership = membershipRepository.findByMember(member)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        membership.setStatus(MembershipStatus.PRO);

        return membershipConverter.toMembershipJoinResponseDto(membership);
    }

    // 멤버십 해지
    public MembershipResponseDto.MembershipWithdrawResponseDto withdrawMembership(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        Membership membership = membershipRepository.findByMember(member)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        membership.setStatus(MembershipStatus.STANDARD);

        return membershipConverter.toMembershipWithdrawResponseDto(membership);
    }
}
