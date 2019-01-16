package biod.rabbitmq.consumer.mailconsumer.service;

import biod.rabbitmq.consumer.mailconsumer.common.util.ConfirmUtil;
import biod.rabbitmq.consumer.mailconsumer.entity.MailInfo;
import biod.rabbitmq.consumer.mailconsumer.mapper.MailMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class RabbitReceiver {
    @Autowired
    private MailSender mailSender;

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
        String returnUrl = null;
        if (headers.get("returnUrl") != null) returnUrl = headers.get("returnUrl").toString();

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);


        Timestamp sendTime = new Timestamp(new Date().getTime());

        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

        MailInfo mailInfo = mailMapper.getMailInfo(mailUid);
        if (mailInfo != null) {
            mailInfo.setSendTime(sendTime);
            mailInfo.setMailStatus(1);
            mailMapper.update(mailInfo);
        }

        if (returnUrl != null){
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("mail_uid", mailUid);

            ConfirmUtil confirmUtil = new ConfirmUtil(returnUrl, map);
            confirmUtil.confirmRequest();
        }
    }
}
