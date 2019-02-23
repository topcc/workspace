package biod.rabbitmq.consumer.mailconsumer.service;

import biod.rabbitmq.consumer.mailconsumer.common.util.RebackUtil;
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

    @Value("${spring.mail.username}") // 默认邮箱
    private String sender;

    @RabbitListener(bindings = @QueueBinding(
            // 设置Exchange Queue Key
            value = @Queue(value = "queue-mail", durable = "true"),
            exchange = @Exchange(value = "exchange-mail", durable = "true",
                    type = "topic", ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMail(Message message, Channel channel) throws Exception{
        // 获取传递的参数
        MessageHeaders headers = message.getHeaders();
        String address = headers.get("address").toString();
        String title = headers.get("title").toString();
        String body = headers.get("body").toString();
        String mailUid = headers.get("mailUid").toString();
        String returnUrl = null;
        if (headers.get("returnUrl") != null) returnUrl = headers.get("returnUrl").toString();

        // 确认收到消息 并回调
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

        // 邮件配置
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(address);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(body);
        mailSender.send(simpleMailMessage);

        // 更新数据库中的邮件信息
        Timestamp sendTime = new Timestamp(new Date().getTime());
        MailInfo mailInfo = mailMapper.getMailInfo(mailUid);
        if (mailInfo != null) {
            mailInfo.setSendTime(sendTime);
            mailInfo.setMailStatus(1);
            mailMapper.update(mailInfo);
        }

        // 若传来对方的回调函数 则将信息反馈给对方
        if (returnUrl != null){
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("mail_uid", mailUid);

            RebackUtil rebackUtil = new RebackUtil(returnUrl, map);
            rebackUtil.confirmRequest();
        }
    }
}
