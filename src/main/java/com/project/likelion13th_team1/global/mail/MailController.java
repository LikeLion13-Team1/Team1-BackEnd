package com.project.likelion13th_team1.global.mail;

import com.project.likelion13th_team1.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail-verifications")
@Tag(name = "SMTP 인증")
public class MailController {
    private final MailService mailService;

    @Operation(summary = "인증 번호 보내기")
    @GetMapping("/request-code")
    public CustomResponse<String> sendVerificationCode(
            @RequestParam String email
    ) {
        mailService.sendVerificationCode(email);
        return CustomResponse.onSuccess("인증 코드가 전송되었습니다.");
    }

    @Operation(summary = "이메일 링크 유효성 판단")
    @GetMapping("/validation")
    public CustomResponse<String> verifyEmailCode(
            @RequestParam String email,
            @RequestParam String code
    ) {
        return CustomResponse.onSuccess(mailService.validateEmail(email, code));
    }
}
