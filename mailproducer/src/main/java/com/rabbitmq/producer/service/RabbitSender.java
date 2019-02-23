package com.rabbitmq.producer.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class RabbitSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    final private RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println("correlationData: " + correlationData);
            System.out.println("ack: " + b);
            if (!b){
                System.out.println("异常处理");
            }
        }
    };

    final private RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int i, String s, String s1, String s2) {
            System.out.println("exchange: " + s1);
            System.out.println("routingKey: " + s2);
        }
    };

    public void send(Object message, Map<String, Object> properties) throws Exception{
        // 将参数Map封装进Message
        MessageHeaders messageHeaders = new MessageHeaders(properties);
        Message msg = MessageBuilder.createMessage(message, messageHeaders);
        // 添加Callback内容
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        // 添加相关参数 这里仅添加Uid便于查看
        CorrelationData correlationData = new CorrelationData("rabbit-" + UUID.randomUUID());
        // 发送至消息队列
        rabbitTemplate.convertAndSend("exchange-mail", "springboot.mail", msg, correlationData);
    }
}
