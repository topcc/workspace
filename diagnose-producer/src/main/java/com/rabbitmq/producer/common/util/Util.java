package com.rabbitmq.producer.common.util;

public class Util {
    public static boolean emailFormat(String email) {
        return email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+");
    }
}
