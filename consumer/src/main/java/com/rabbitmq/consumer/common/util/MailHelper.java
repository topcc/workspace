package com.rabbitmq.consumer.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailHelper {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    private String address;
    private String title;
    private String body;

    public MailHelper(String address, String title, String body){
        this.address = address;
        this.title = title;
        this.body = body;
    }

    public void send(){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);
    }
}
