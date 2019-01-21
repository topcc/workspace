package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.common.exception.MyException;
import com.rabbitmq.producer.common.util.Util;
import com.rabbitmq.producer.entity.DiagnoseInfo;
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
@RequestMapping("/biod/ecgdiagnose")
public class MailController {
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private MailMapper mailMapper;
    //String diagnoseUid, String fileUid, String filePath, String userMail, String returnUrl
    @RequestMapping("/class2")
    public Map<String, String> sendMail(@RequestParam(name = "diagnoseUid") String diagnoseUid,
                                        @RequestParam(name = "fileUid") String fileUid,
                                        @RequestParam(name = "filePath") String filePath,
                                        @RequestParam(name = "userMail") String userMail,
                                        @RequestParam(name = "returnUrl", required = false) String returnUrl) throws Exception {

        if (!Util.emailFormat(userMail)) throw new MyException("500", "Error in mail address format");

        Map<String, Object> properties = new HashMap<>();
        properties.put("diagnoseUid", diagnoseUid);
        properties.put("fileUid", fileUid);
        properties.put("filePath", filePath);
        properties.put("userMail", userMail);
        properties.put("returnUrl", returnUrl);
        rabbitSender.send("Mail", properties);

        DiagnoseInfo diagnoseInfo = new DiagnoseInfo(diagnoseUid, fileUid, filePath, userMail, returnUrl);
        mailMapper.insert(diagnoseInfo);

        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "successfully");
        return res;
    }
}
