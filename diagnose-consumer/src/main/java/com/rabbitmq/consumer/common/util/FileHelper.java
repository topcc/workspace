package com.rabbitmq.consumer.common.util;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileHelper {
    private String fileUid;
    private String filePath;
    private String localPath;

    public FileHelper(String fileUid, String filePath, String localPath){
        this.fileUid = fileUid;
        this.filePath = filePath;
        this.localPath = localPath;
    }

    public String fileDownload() throws MalformedURLException {
        // 下载网络文件
        int byteread = 0;

        URL url = new URL(filePath);
        String file = localPath + fileUid + ".csv";
        try {
            URLConnection connection = url.openConnection();
            InputStream inStream = connection.getInputStream();

            FileOutputStream fileOutputStream = new FileOutputStream(file);

            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, byteread);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

        return file + " ";
    }

    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }
}
