package com.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.common.util.CmdHelper;
import com.rabbitmq.consumer.common.util.FileHelper;
import com.rabbitmq.consumer.entity.DiagnoseInfo;
import com.rabbitmq.consumer.mapper.DiagnoseMapper;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class RabbitReceiver {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private DiagnoseMapper diagnoseMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-diagnose", durable = "true"),
            exchange = @Exchange(value = "exchange-diagnose", durable = "true", type = "topic", ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMail(Message message, Channel channel) throws Exception{
        MessageHeaders headers = message.getHeaders();
        String taskUid = headers.get("taskUid").toString();
        String diagnoseUid = headers.get("diagnoseUid").toString();
        String fileUid = headers.get("fileUid").toString();
        String filePath = headers.get("filePath").toString();
        String userMail = headers.get("userMail").toString();
        String returnUrl = headers.get("returnUrl").toString();
        String model = headers.get("model").toString();

        String localPath = "/var/www/ecg-file/";
        FileHelper fileHelper = new FileHelper(fileUid, filePath, localPath);
        String file = fileHelper.fileDownload();

        String cmd = model + file + localPath + "result";
        System.out.println(cmd);
        CmdHelper cmdHelper = new CmdHelper();
        String resultStr = cmdHelper.runCommand(cmd);
        System.out.println(resultStr);

        String resultPath = localPath + "result/" + fileUid + ".csv.json";
        String result = fileHelper.readToString(resultPath);
        System.out.println(result);

        Timestamp finishTime = new Timestamp(new Date().getTime());

        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

        DiagnoseInfo diagnoseInfo = diagnoseMapper.getDiagnoseInfo(taskUid);
        if (diagnoseInfo != null) {
            diagnoseInfo.setResult(result);
            diagnoseInfo.setFinishTime(finishTime);
            diagnoseInfo.setTaskStatus(1);
            diagnoseMapper.update(diagnoseInfo);
        }

        if (userMail != null){
            System.out.println(userMail);
        }

        if (returnUrl != null){
            System.out.println(returnUrl);
        }
    }
}
