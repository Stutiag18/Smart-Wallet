package com.smartwallet.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Value("${spring.mail.from:adityakanoujia30@gmail.com}")
    private String fromEmail;
    
    private String verificationSenderName = "welcome";
    private String resetSenderName = "reset-wallet";

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String otp) {
        logger.info("PRE-SEND: Attempting to send verification email to {} from {} <{}>", toEmail, verificationSenderName, fromEmail);
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            org.springframework.mail.javamail.MimeMessageHelper helper = new org.springframework.mail.javamail.MimeMessageHelper(message, "UTF-8");
            
            helper.setFrom(fromEmail, verificationSenderName);
            helper.setTo(toEmail);
            helper.setSubject("Smart Wallet - Verify Your Email");
            helper.setText("Welcome to Smart Wallet! Your verification code is: " + otp + 
                           "\n\nThis code will expire in 10 minutes.");
            
            mailSender.send(message);
            logger.info("✅ POST-SEND: Verification email SENT successfully to {}", toEmail);
        } catch (Exception e) {
            logger.error("❌ ERROR-SEND: Failed to send verification email to {}. Error: {}", toEmail, e.getMessage());
            // Log full stack trace for deep debugging
            e.printStackTrace();
        }
    }

    public void sendResetPasswordEmail(String toEmail, String otp) {
        logger.info("PRE-SEND: Attempting to send reset email to {} from {} <{}>", toEmail, resetSenderName, fromEmail);
        try {
            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
            
            helper.setFrom(fromEmail, resetSenderName);
            helper.setTo(toEmail);
            helper.setSubject("Reset Your Password - Smart Wallet");
            helper.setText("Your OTP for password reset is: " + otp, true);
            
            mailSender.send(message);
            logger.info("✅ POST-SEND: Reset email SENT successfully to {}", toEmail);
        } catch (Exception e) {
            logger.error("❌ ERROR-SEND: Failed to send reset email to {}. Error: {}", toEmail, e.getMessage());
            e.printStackTrace();
        }
    }
}
