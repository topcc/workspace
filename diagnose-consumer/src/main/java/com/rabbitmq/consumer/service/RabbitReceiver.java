package com.rabbitmq.consumer.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.consumer.common.util.CmdHelper;
import com.rabbitmq.consumer.common.util.RebackUtil;
import com.rabbitmq.consumer.common.util.FileHelper;
import com.rabbitmq.consumer.entity.DiagnoseInfo;
import com.rabbitmq.consumer.mapper.DiagnoseMapper;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        // 接受Messages
        MessageHeaders headers = message.getHeaders();
        String taskUid = headers.get("taskUid").toString();
        String diagnoseUid = headers.get("diagnoseUid").toString();
        String fileUid = headers.get("fileUid").toString();
        String filePath = headers.get("filePath").toString();
        String returnUrl = headers.get("returnUrl").toString();
        String model = headers.get("model").toString();

        // 手动处理接受信息
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTag, false);

        // 下载文件到localPath下
        String localPath = "/var/www/ecg-file/";
        FileHelper fileHelper = new FileHelper(fileUid, filePath, localPath);
        String file = fileHelper.fileDownload();

        // 调用模型进行诊断
        long startTime = new Date().getTime();

        String cmd = model + file + localPath + "result";
        System.out.println(cmd);
        CmdHelper cmdHelper = new CmdHelper();
        String resultStr = cmdHelper.runCommand(cmd);
        System.out.println(resultStr);

        long endTime = new Date().getTime();
        long useTime = endTime - startTime;

        // 读取json格式的诊断结果
        String resultPath = localPath + "result/" + fileUid + ".csv.json";
        String result = fileHelper.readToString(resultPath);
        System.out.println(result);

        // 更新记录信息
        Timestamp finishTime = new Timestamp(new Date().getTime());
        DiagnoseInfo diagnoseInfo = diagnoseMapper.getDiagnoseInfo(taskUid);
        if (diagnoseInfo != null) {
            diagnoseInfo.setResult(result);
            diagnoseInfo.setFinishTime(finishTime);
            diagnoseInfo.setTaskStatus(1);
            diagnoseMapper.update(diagnoseInfo);
        }

        // 回调函数
        if (returnUrl != null){
            MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
            map.add("task_uid", taskUid);
            map.add("diagnose_uid", diagnoseUid);
            map.add("use_time", useTime + "");
            map.add("result", result);

            RebackUtil rebackUtil = new RebackUtil(returnUrl, map);
            rebackUtil.confirmRequest();
        }
    }
}
