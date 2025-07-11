package com.project.likelion13th_team1.global.mail;

import com.project.likelion13th_team1.global.mail.exception.MailErrorCode;
import com.project.likelion13th_team1.global.mail.exception.MailException;
import com.project.likelion13th_team1.global.security.auth.dto.response.AuthResponseDto;
import com.project.likelion13th_team1.global.security.exception.AuthErrorCode;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeMessage;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public void sendVerificationCode(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        String code = createVerificationCode();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom("dlrbdjs7@naver.com");
            helper.setTo(email); // <-- 전달받은 수신자 이메일
            helper.setSubject("살리미 - 이메일 인증 코드");

            String redisKey = "verification_code : " + email;

            // 인증 코드를 포함한 HTML 콘텐츠 생성
            String content = """
                    <!DOCTYPE html>
                    <html>
                    <body>
                    <div style="margin:100px;">
                        <h1> 안녕하세요 :) </h1>
                        <br>
                        <p>아래 인증 코드를 입력해주세요.</p>
                        <div align="center" style="border:1px solid black; padding:10px;">
                            <h2>%s</h2>
                        </div>
                        <p>해당 코드는 5분간 유효합니다.</p>
                    </div>
                    </body>
                    </html>
                    """.formatted(code);

            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
            redisTemplate.opsForValue().set(redisKey, code, 300000, TimeUnit.MILLISECONDS);
            log.info("메일 발송 성공! 대상: {}, 코드: {}", email, code);

        } catch (AddressException ae) {
            throw new MailException(MailErrorCode.WRONG_EMAIL_FORMAT);
        } catch (Exception e) {
            log.error("메일 발송 실패!", e);
            throw new RuntimeException("메일 발송 중 오류 발생", e);
        }
    }

    // 내부 사용 메서드
    private String createVerificationCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d", random.nextInt(1000000));
    }

    public void verifyEmail(String email, String inputCode) {
        String redisKey = "verification_code : " + email;

        final String code = redisTemplate.opsForValue().get(redisKey);

        if (code == null) {
            throw new MailException(MailErrorCode.EMAIL_MANIPULATED);
        }
        if (!code.equals(inputCode)) {
            throw new MailException(MailErrorCode.WRONG_CODE);
        }

        // 멤버객체에 인증완료 ENUM을 할 수 있겠군요
        redisTemplate.delete(redisKey);
    }

    public AuthResponseDto.PasswordTokenResponseDto validateToChangePassword(String email, String inputCode) {
        String redisKey = "verification_code : " + email;

        final String code = redisTemplate.opsForValue().get(redisKey);

        if (code == null) {
            throw new MailException(MailErrorCode.EMAIL_MANIPULATED);
        }
        if (!code.equals(inputCode)) {
            throw new MailException(MailErrorCode.WRONG_CODE);
        }

        // 멤버객체에 인증완료 ENUM을 할 수 있겠군요
        redisTemplate.delete(redisKey);

        final String uuid = UUID.randomUUID().toString();

        redisKey = "password_token : " + uuid;

        redisTemplate.opsForValue().set(redisKey, email, 300, TimeUnit.SECONDS);

        return AuthResponseDto.PasswordTokenResponseDto.builder()
                .uuid(uuid)
                .build();
    }

    public void sendPasswordChangeNotification(String email) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");

            helper.setFrom("dlrbdjs7@naver.com");
            helper.setTo(email); // <-- 전달받은 수신자 이메일
            helper.setSubject("살리미 - 비밀번호 변경 안내");

            // 인증 코드를 포함한 HTML 콘텐츠 생성
            String content = """
                    <!DOCTYPE html>
                    <html>
                    <body>
                    <div style="margin:100px;">
                        <h1> 안녕하세요 :) </h1>
                        <br>
                        <div align="center" style="border:1px solid black; padding:10px;">
                            <h2>비밀번호가 변경 되었습니다.</h2>
                        </div>
                    </div>
                    </body>
                    </html>
                    """;

            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
            log.info("메일 발송 성공! 대상: {}", email);

        } catch (AddressException ae) {
            throw new MailException(MailErrorCode.WRONG_EMAIL_FORMAT);
        } catch (Exception e) {
            log.error("메일 발송 실패!", e);
            throw new RuntimeException("메일 발송 중 오류 발생", e);
        }
    }
}
