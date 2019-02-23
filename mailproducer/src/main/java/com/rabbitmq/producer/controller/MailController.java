package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.common.exception.MyException;
import com.rabbitmq.producer.common.util.Util;
import com.rabbitmq.producer.entity.MailInfo;
import com.rabbitmq.producer.mapper.MailMapper;
import com.rabbitmq.producer.service.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/biod")
public class MailController {
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private MailMapper mailMapper;

    @RequestMapping("/mail")
    public Map<String, String> sendMail(@RequestParam(name = "address") String address,
                                        @RequestParam(name = "title") String title,
                                        @RequestParam(name = "body") String body,
                                        @RequestParam(name = "returnUrl", required = false) String returnUrl) throws Exception {

        // 验证邮箱格式
        if (!Util.emailFormat(address)) throw new MyException("500", "Error in mail address format");

        // 生成唯一Uid
        String mailUid = UUID.randomUUID().toString();

        // 丢入队列的参数Map
        Map<String, Object> properties = new HashMap<>();
        properties.put("mailUid", mailUid);
        properties.put("address", address);
        properties.put("title", title);
        properties.put("body", body);
        properties.put("returnUrl", returnUrl);
        rabbitSender.send("Mail", properties);

        // 记录邮件信息
        MailInfo mailInfo = new MailInfo(mailUid, address, title, body, returnUrl);
        mailMapper.insert(mailInfo);

        // 返回信息
        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "Send successfully");
        res.put("mailUid", mailUid);
        return res;
    }

    // 连接测试
    @RequestMapping("/index")
    public Object index(@RequestParam(name = "id") int id){
        MailInfo mailInfo = mailMapper.getMailInfo(id);
        if (mailInfo == null){
            return "这不是一个有效数据";
        }
        else {
            return mailInfo;
        }
    }
}
