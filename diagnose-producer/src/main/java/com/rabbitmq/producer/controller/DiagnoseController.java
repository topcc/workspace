package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.common.exception.MyException;
import com.rabbitmq.producer.common.util.Util;
import com.rabbitmq.producer.entity.DiagnoseInfo;
import com.rabbitmq.producer.mapper.DiagnoseMapper;
import com.rabbitmq.producer.service.RabbitSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/ecg/dp")
public class DiagnoseController {
    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private DiagnoseMapper diagnoseMapper;

    @RequestMapping("/class2/v1")
    public Map<String, String> sendMail(@RequestParam(name = "diagnoseUid") String diagnoseUid,
                                        @RequestParam(name = "fileUid") String fileUid,
                                        @RequestParam(name = "filePath") String filePath,
                                        @RequestParam(name = "returnUrl", required = false) String returnUrl) throws Exception {

        String taskUid = UUID.randomUUID().toString();
        String model = "python /notebooks/ecg-diagnose.py ";

        Map<String, Object> properties = new HashMap<>();
        properties.put("taskUid", taskUid);
        properties.put("diagnoseUid", diagnoseUid);
        properties.put("fileUid", fileUid);
        properties.put("filePath", filePath);
        properties.put("returnUrl", returnUrl);
        properties.put("model", model);
        rabbitSender.send("Diagnose", properties);

        DiagnoseInfo diagnoseInfo = new DiagnoseInfo();
        diagnoseInfo.setTaskUid(taskUid);
        diagnoseInfo.setDiagnoseUid(diagnoseUid);
        diagnoseInfo.setFileUid(fileUid);
        diagnoseInfo.setFilePath(filePath);
        diagnoseInfo.setReturnUrl(returnUrl);
        diagnoseMapper.insert(diagnoseInfo);

        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "successfully");
        res.put("taskUid", taskUid);
        return res;
    }
}
