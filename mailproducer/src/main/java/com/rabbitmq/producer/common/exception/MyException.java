package com.rabbitmq.producer.common.exception;

public class MyException extends RuntimeException {
    private String code;  //异常状态码

    private String message;  //异常信息

    public MyException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
