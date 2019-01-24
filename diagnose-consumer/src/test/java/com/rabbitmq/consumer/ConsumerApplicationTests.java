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
import java.sql.Timestamp;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerApplicationTests {

    @Autowired
    private DiagnoseMapper diagnoseMapper;

    @Value("${spring.mail.username}")
    private String sender;

    @Test
    public void contextLoads() {
        Timestamp finishTime = new Timestamp(new Date().getTime());
        DiagnoseInfo diagnoseInfo = diagnoseMapper.getDiagnoseInfo("2d8c41c9-a2d9-456b-8412-53d6d428396d");
        if (diagnoseInfo != null) {
            diagnoseInfo.setResult("[{\"prob\": 0.006, \"label\": \"01000000\"}, {\"prob\": 0.994, \"label\": \"02000000\"}]");
            diagnoseInfo.setFinishTime(finishTime);
            diagnoseInfo.setTaskStatus(1);
            diagnoseMapper.update(diagnoseInfo);
        }
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

