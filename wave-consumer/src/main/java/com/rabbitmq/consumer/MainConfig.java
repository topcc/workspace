package com.rabbitmq.consumer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.rabbitmq.consumer.*"})
public class MainConfig {
}
