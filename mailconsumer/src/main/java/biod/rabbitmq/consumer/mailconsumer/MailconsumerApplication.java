package biod.rabbitmq.consumer.mailconsumer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"biod.rabbitmq.consumer.mailconsumer.mapper"})
public class MailconsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailconsumerApplication.class, args);
    }

}

