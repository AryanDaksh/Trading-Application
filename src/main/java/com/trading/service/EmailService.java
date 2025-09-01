package com.trading.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Aryan Daksh
 * @version 1.0
 * @since 09-08-2025 21:53
 */
@Service
@RequiredArgsConstructor
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendVerificationOtpMail(final String email, final String otp) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Email Verification OTP";
        String content = "Your OTP for email verification is: " + otp;

        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.setTo(email);

        try {
            javaMailSender.send(mimeMessage);
        } catch (MailException e) {
            throw new MailSendException("Failed to send email", e);
        }
    }
}
