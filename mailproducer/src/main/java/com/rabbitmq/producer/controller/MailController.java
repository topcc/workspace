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

        if (!Util.emailFormat(address)) throw new MyException("1", "Error in mail address format");

        String mailUid = UUID.randomUUID().toString();

        Map<String, Object> properties = new HashMap<>();
        properties.put("mailUid", mailUid);
        properties.put("address", address);
        properties.put("title", title);
        properties.put("body", body);
        properties.put("returnUrl", returnUrl);
        rabbitSender.send("Mail", properties);

        MailInfo mailInfo = new MailInfo(mailUid, address, title, body, returnUrl);
        mailMapper.insert(mailInfo);

        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "Send successfully");
        res.put("mailUid", mailUid);
        return res;
    }

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
