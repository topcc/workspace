package com.rabbitmq.consumer;

import com.rabbitmq.consumer.entity.DiagnoseInfo;
import com.rabbitmq.consumer.mapper.DiagnoseMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {

    @Autowired
    private DiagnoseMapper diagnoseMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @Test
    public void contextLoads() {
    }

    @Ignore
    public void fileDownload() throws MalformedURLException {
        // 下载网络文件
        int bytesum = 0;
        int byteread = 0;

        URL url = new URL("http://api.biodwhu.cn/ecg/ecgfile/fileDownload/file_uid/1545740596167306");

        try {
            URLConnection connection = url.openConnection();
            InputStream inStream = connection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream("e:/123.csv");

            byte[] buffer = new byte[1204];
            int length;
            while ((byteread = inStream.read(buffer)) != -1) {
                bytesum += byteread;
                //System.out.println(bytesum);
                fileOutputStream.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

