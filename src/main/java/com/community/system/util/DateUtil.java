package com.community.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date getDate(String date) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.parse(date);
    }
}
