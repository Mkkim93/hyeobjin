package com.hyeobjin.application.common.service.inquiry.mail;

import com.hyeobjin.application.common.service.users.AdminMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InquiryMailService {

    @Value("${spring.mail.username}")
    private String username;

    private final JavaMailSender javaMailSender;
    private final AdminMailService adminMailService;

    @Autowired
    public InquiryMailService(JavaMailSender javaMailSender, AdminMailService adminMailService) {
        this.javaMailSender = javaMailSender;
        this.adminMailService = adminMailService;
    }

    public void sendEmail(String title, String content) throws MessagingException {

        List<String> recipient = adminMailService.findAdminMailList();

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(username);
        helper.setTo(recipient.toArray(new String[0]));
        helper.setSubject(content);
        helper.setText(title, true);

        javaMailSender.send(message);

        log.info("📨 메일 전송 완료! 수신자: {}", recipient);
    }
}
