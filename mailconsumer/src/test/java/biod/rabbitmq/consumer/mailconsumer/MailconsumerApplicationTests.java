package biod.rabbitmq.consumer.mailconsumer;

import biod.rabbitmq.consumer.mailconsumer.common.util.ConfirmUtil;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailconsumerApplicationTests {
    @Test
    public void contextLoads() {
        String url = "http://api.biodwhu.cn/ecg/mailmessage/confirmMail";
        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("mail_uid", "2c8e2378-c860-4de8-9d7a-8b3dc666cdf6");

        ConfirmUtil confirmUtil = new ConfirmUtil(url, map);
        confirmUtil.confirmRequest();
    }

}

