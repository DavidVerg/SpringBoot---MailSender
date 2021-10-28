package com.example.reportes.service;

import com.example.reportes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@example.com");
        message.setTo("Davinchu.1@gmail.com");
        message.setSubject("Example mail");
        message.setText("Lore ipsum...");

        emailSender.send(message);

    }

    public void sendMessageWithAttachment() throws MessagingException {
        // ...

        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo("Davinchu.1@gmail.com");
        helper.setSubject("Example mail");
        helper.setText("Lore ipsum...");

        FileSystemResource file
                = new FileSystemResource(new File("HolaMundo.pdf"));
        helper.addAttachment("HolaMundo.pdf", file);

        emailSender.send(message);
        // ...
    }

    public void sendMessageFromTemplate(User user, String html) throws MessagingException {
        javax.mail.internet.MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome " + user.getName());

        helper.setText(html, true);
        helper.setTo(user.getEmail());
        //System.out.println(user.getName() + " " + user.getEmail());
        emailSender.send(mimeMessage);
    }
}