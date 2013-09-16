package com.gap.metrics.service;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Repository;
import org.springframework.mail.javamail.JavaMailSender;


@Repository
public class EmailService {

//    @Autowired
//    @Setter
//    private JavaMailSender mailSender;

    public void sendMail(String to, String from, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(body);
        //mailSender.send(message);
    }
}
