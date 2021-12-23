package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
@Slf4j
public class EmailUtil {

    @Value("${application.mail.no-reply-address:sphokuhlemkhwanazi@gmail.com}")
    private String noreplyAddress;

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String subject) {
        try {
            String body = "<html><h1>Greetings from the Equator around the Atlas.</h1> </br>" +
                    "<table>"+
                    "<tr><td>Table of content</td></tr>"+
                    "<tr><td>First Name</td><td>Last Name</td></tr>"+
                    "<tr><td>S'phokuhle</td><td>Mkhwanazi</td></tr>"+
                    "</table>"+
                    "</html>";
            String to = "jujulijojo@gmail.com";//"user1@demo.com";

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(noreplyAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.addAttachment("file.txt", new File("file.txt"));
            helper.addAttachment("file2.txt", new File("file2.txt"));
            javaMailSender.send(message);

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }
}
