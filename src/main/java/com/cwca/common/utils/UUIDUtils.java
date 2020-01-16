package com.cwca.common.utils;

import java.util.Date;
import java.util.UUID;

/**
 * 获取uuid
 *
 * @author wong
 */
public class UUIDUtils {

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

    public static String getNumID() {
        Date date = new Date();
        return String.valueOf(date.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getUUID());
    }
}
