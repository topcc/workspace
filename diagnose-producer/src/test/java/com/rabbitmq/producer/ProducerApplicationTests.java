package com.rabbitmq.producer;

import com.rabbitmq.producer.entity.DiagnoseInfo;
import com.rabbitmq.producer.mapper.DiagnoseMapper;
import com.rabbitmq.producer.service.RabbitSender;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Autowired
    private RabbitSender rabbitSender;

    @Autowired
    private DiagnoseMapper diagnoseMapper;

}

