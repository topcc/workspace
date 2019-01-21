package com.rabbitmq.consumer.common.util;

import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class CmdHelper {
    public String runCommand(String cmd) {
        StringBuffer buf = new StringBuffer(1000);
        String rt = "-1";
        try {
            Process pos = Runtime.getRuntime().exec(cmd);
            pos.waitFor();

            InputStreamReader ir = new InputStreamReader(pos.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String ln = "";
            while ((ln = input.readLine()) != null) {
                buf.append(ln);
            }
            rt = buf.toString();
            input.close();
            ir.close();

        } catch (Exception e) {
            rt = e.toString();
        }
        return rt;
    }
}
