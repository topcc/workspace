package com.rabbitmq.producer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.rabbitmq.producer.*"})
public class MainConfig {
}
