package com.fsb.eblood.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {
private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email) {
            try{

                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
                helper.setText(email,true);
                helper.setTo(to);
                helper.setSubject("Confirm your email");
                helper.setFrom("ayachni.blood.donation@gmail.com");
                javaMailSender.send(mimeMessage);

            }catch (Exception e){
                LOGGER.error("failed to send email "+e);
                throw new IllegalStateException("failed to send email");

            }


    }
}


