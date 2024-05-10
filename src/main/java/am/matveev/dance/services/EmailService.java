package am.matveev.dance.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${admin.email}")
    private String adminEmail;

    @Transactional
    public boolean sendEmailToAdmin(String userEmail, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(adminEmail);
        mailMessage.setSubject("New message from website contact form");
        mailMessage.setText("From: " + userEmail + "\n\n" + message);

        try {
            javaMailSender.send(mailMessage);
            return true;
        } catch (MailException e) {
            log.error("Failed to send email: {}", e.getMessage());
            return false;
        }
    }
}

