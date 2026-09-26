package com.pratice.sevice;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender javaMailSender;

    public void sendEmail(
            String to,
            String bcc,
            String cc,
            String subject,
            String body,
            MultipartFile file
    ) throws MessagingException, IOException {

        if (to == null || to.isBlank()) {
            throw new IllegalArgumentException("Recipient email is required");
        }

        MimeMessage message = javaMailSender.createMimeMessage();

        boolean multipart = file != null && !file.isEmpty();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, multipart);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);

        if (bcc != null && !bcc.isBlank()) {
            helper.setBcc(bcc);
        }

        if (cc != null && !cc.isBlank()) {
            helper.setCc(cc);
        }

        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();

            ByteArrayResource resource =
                    new ByteArrayResource(file.getBytes());

            helper.addAttachment(fileName, resource);
        }

        javaMailSender.send(message);
    }
}