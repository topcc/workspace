package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.common.exception.MyException;
import com.rabbitmq.producer.common.util.Util;
import com.rabbitmq.producer.entity.WaveInfo;
import com.rabbitmq.producer.mapper.MailMapper;
import com.rabbitmq.producer.service.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/biod")
public class MailController {
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private MailMapper mailMapper;

    @RequestMapping("/mail")
    public Map<String, String> sendMail(@RequestParam(name = "ecgUid") String ecgUid,
                                        @RequestParam(name = "fileUid") String fileUid,
                                        @RequestParam(name = "filePath") String filePath,
                                        @RequestParam(name = "returnUrl", required = false) String returnUrl) throws Exception {

        if (!Util.emailFormat(filePath)) throw new MyException("500", "Not a valid download link");

        Map<String, Object> properties = new HashMap<>();
        properties.put("ecgUid", ecgUid);
        properties.put("fileUid", fileUid);
        properties.put("filePath", filePath);
        properties.put("returnUrl", returnUrl);
        rabbitSender.send("Wave", properties);

        WaveInfo waveInfo = new WaveInfo(ecgUid, fileUid, filePath, returnUrl);
        mailMapper.insert(waveInfo);

        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "successfully");
        return res;
    }
}
