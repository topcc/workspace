package com.rabbitmq.producer.controller;

import com.rabbitmq.producer.entity.DiagnoseInfo;
import com.rabbitmq.producer.entity.Model;
import com.rabbitmq.producer.mapper.DiagnoseMapper;
import com.rabbitmq.producer.mapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping("/class2")
    public Map<String, String> sendMail(@RequestParam(name = "diagnoseUid") String diagnoseUid,
                                        @RequestParam(name = "fileUid") String fileUid,
                                        @RequestParam(name = "filePath") String filePath,
                                        @RequestParam(name = "modelId") String modelId,
                                        @RequestParam(name = "returnUrl", required = false) String returnUrl) throws Exception {

        // 生成唯一Uid
        String taskUid = UUID.randomUUID().toString();
        // 默认模型
        String modelPath = "python /notebooks/ecg-diagnose.py";
        // 从数据库读取模型
        Model model = modelMapper.getModelByMId(modelId);
        if (model != null){
            modelPath = model.getModelPath();
        }
        modelPath = modelPath + " ";

        // 加入队列的参数Map
        Map<String, Object> properties = new HashMap<>();
        properties.put("taskUid", taskUid);
        properties.put("diagnoseUid", diagnoseUid);
        properties.put("fileUid", fileUid);
        properties.put("filePath", filePath);
        properties.put("returnUrl", returnUrl);
        properties.put("modelPath", modelPath);
        rabbitSender.send("Diagnose", properties);

        // 记录诊断信息
        DiagnoseInfo diagnoseInfo = new DiagnoseInfo();
        diagnoseInfo.setTaskUid(taskUid);
        diagnoseInfo.setDiagnoseUid(diagnoseUid);
        diagnoseInfo.setFileUid(fileUid);
        diagnoseInfo.setFilePath(filePath);
        diagnoseInfo.setReturnUrl(returnUrl);
        diagnoseMapper.insert(diagnoseInfo);

        // 返回信息
        Map<String, String> res = new HashMap<>();
        res.put("code", "200");
        res.put("msg", "successfully");
        res.put("taskUid", taskUid);
        return res;
    }

    // 连接测试
    @RequestMapping("/index")
    public Object index(@RequestParam(name = "modelId") String modelId){
        Model model = modelMapper.getModelByMId(modelId);
        if (model == null){
            return "这不是一个有效数据";
        }
        else {
            return model;
        }
    }
}
