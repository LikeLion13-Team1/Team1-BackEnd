package com.project.likelion13th_team1.global.security.kakao.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoUserInfoResponseDto(
        //회원 번호
        @JsonProperty("id")
        Long id,

        //자동 연결 설정을 비활성화한 경우만 존재.
        //true : 연결 상태, false : 연결 대기 상태
        @JsonProperty("has_signed_up")
        Boolean hasSignedUp,

        //서비스에 연결 완료된 시각. UTC
        @JsonProperty("connected_at")
        Date connectedAt,

        //카카오싱크 간편가입을 통해 로그인한 시각. UTC
        @JsonProperty("synched_at")
        Date synchedAt,

        //사용자 프로퍼티
        @JsonProperty("properties")
        HashMap<String, String> properties,

        //카카오 계정 정보
        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount,

        //uuid 등 추가 정보
        @JsonProperty("for_partner")
        Partner partner
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record KakaoAccount(
            //프로필 정보 제공 동의 여부
            @JsonProperty("profile_needs_agreement")
            Boolean isProfileAgree,

            //닉네임 제공 동의 여부
            @JsonProperty("profile_nickname_needs_agreement")
            Boolean isNickNameAgree,

            //프로필 사진 제공 동의 여부
            @JsonProperty("profile_image_needs_agreement")
            Boolean isProfileImageAgree,

            //사용자 프로필 정보
            @JsonProperty("profile")
            Profile profile,

            //이름 제공 동의 여부
            @JsonProperty("name_needs_agreement")
            Boolean isNameAgree,

            //카카오계정 이름
            @JsonProperty("name")
            String name,

            //이메일 제공 동의 여부
            @JsonProperty("email_needs_agreement")
            Boolean isEmailAgree,

            //이메일이 유효 여부
            // true : 유효한 이메일, false : 이메일이 다른 카카오 계정에 사용돼 만료
            @JsonProperty("is_email_valid")
            Boolean isEmailValid,

            //이메일이 인증 여부
            //true : 인증된 이메일, false : 인증되지 않은 이메일
            @JsonProperty("is_email_verified")
            Boolean isEmailVerified,

            //카카오계정 대표 이메일
            @JsonProperty("email")
            String email,

            //연령대 제공 동의 여부
            @JsonProperty("age_range_needs_agreement")
            Boolean isAgeAgree,

            //연령대
            //참고 https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
            @JsonProperty("age_range")
            String ageRange,

            //출생 연도 제공 동의 여부
            @JsonProperty("birthyear_needs_agreement")
            Boolean isBirthYearAgree,

            //출생 연도 (YYYY 형식)
            @JsonProperty("birthyear")
            String birthYear,

            //생일 제공 동의 여부
            @JsonProperty("birthday_needs_agreement")
            Boolean isBirthDayAgree,

            //생일 (MMDD 형식)
            @JsonProperty("birthday")
            String birthDay,

            //생일 타입
            // SOLAR(양력) 혹은 LUNAR(음력)
            @JsonProperty("birthday_type")
            String birthDayType,

            //성별 제공 동의 여부
            @JsonProperty("gender_needs_agreement")
            Boolean isGenderAgree,

            //성별
            @JsonProperty("gender")
            String gender,

            //전화번호 제공 동의 여부
            @JsonProperty("phone_number_needs_agreement")
            Boolean isPhoneNumberAgree,

            //전화번호
            //국내 번호인 경우 +82 00-0000-0000 형식
            @JsonProperty("phone_number")
            String phoneNumber,

            //CI 동의 여부
            @JsonProperty("ci_needs_agreement")
            Boolean isCIAgree,

            //CI, 연계 정보
            @JsonProperty("ci")
            String ci,

            //CI 발급 시각, UTC
            @JsonProperty("ci_authenticated_at")
            Date ciCreatedAt
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Profile(
                //닉네임
                @JsonProperty("nickname")
                String nickName,

                //프로필 미리보기 이미지 URL
                @JsonProperty("thumbnail_image_url")
                String thumbnailImageUrl,

                //프로필 사진 URL
                @JsonProperty("profile_image_url")
                String profileImageUrl,

                //프로필 사진 URL 기본 프로필인지 여부
                //true : 기본 프로필, false : 사용자 등록
                @JsonProperty("is_default_image")
                String isDefaultImage,

                //닉네임이 기본 닉네임인지 여부
                //true : 기본 닉네임, false : 사용자 등록
                @JsonProperty("is_default_nickname")
                Boolean isDefaultNickName
        ) {
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Partner(
            //고유 ID
            @JsonProperty("uuid")
            String uuid
    ) {
    }
}