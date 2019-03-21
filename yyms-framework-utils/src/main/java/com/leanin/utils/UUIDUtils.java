package com.leanin.utils;

import java.util.UUID;

/**
 * UUID工具类
 */
public class UUIDUtils {

    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
