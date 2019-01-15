package com.rabbitmq.producer.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyControllerAdvice {
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Map<String, Object> myExceptionHandler1(MyException myex){
        Map<String, Object> res = new HashMap<>();

        res.put("code", myex.getCode());
        res.put("msg", myex.getMessage());

        return res;
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> myExceptionHandler2(Exception ex){
        Map<String, Object> res = new HashMap<>();

        res.put("code", 500);
        res.put("msg", ex.getMessage());

        return res;
    }
}
