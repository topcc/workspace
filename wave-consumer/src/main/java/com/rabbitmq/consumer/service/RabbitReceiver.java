package com.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.common.util.MailHelper;
import com.rabbitmq.consumer.entity.MailInfo;
import com.rabbitmq.consumer.mapper.MailMapper;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

@Component
public class RabbitReceiver {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailMapper mailMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-mail", durable = "true"),
            exchange = @Exchange(value = "exchange-mail", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMail(Message message, Channel channel) throws Exception{
        MessageHeaders headers = message.getHeaders();
        String address = headers.get("address").toString();
        String title = headers.get("title").toString();
        String body = headers.get("body").toString();
        String mailUid = headers.get("mailUid").toString();
        String returnUrl = headers.get("returnUrl").toString();

        MailHelper mailHelper = new MailHelper(address, title, body);
        //mailHelper.send();


        Date sendTime = new Date();

        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

        MailInfo mailInfo = mailMapper.getMailInfo(mailUid);
        if (mailInfo != null) {
            mailInfo.setSendTime(sendTime);
            mailInfo.setMailStatus(1);
            mailMapper.update(mailInfo);
        }

        if (returnUrl != null){

        }
    }
}
