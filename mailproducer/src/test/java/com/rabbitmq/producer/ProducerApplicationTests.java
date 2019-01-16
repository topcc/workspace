package com.rabbitmq.producer;

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

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Ignore
    public void testMail() throws Exception{
        Map<String, Object> properties = new HashMap<>();
        properties.put("address", "799792309@qq.com");
        properties.put("title", "这是标题");
        properties.put("body", "这是正文");
        properties.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("Mail", properties);
    }
}

