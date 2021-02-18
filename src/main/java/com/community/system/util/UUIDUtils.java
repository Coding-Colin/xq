package com.community.system.util;

import java.util.UUID;

public class UUIDUtils {

    /**
     * 随机获取验证码
     * @return
     */
    public static String postUUID(){
        String uuid = UUID.randomUUID().toString().replaceAll("-","").substring(6,12);
        return uuid;
    }


}
