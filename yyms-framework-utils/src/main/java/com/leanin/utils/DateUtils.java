package com.leanin.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @ProjectName: yyms
 * @Package: com.leanin.utils
 * @ClassName: DateUtils
 * @Author: zd
 * @Description: Date工具类
 * @Date: 2019/5/19 23:05
 * @Version: 1.0
 */
public class DateUtils {


    /**
     *@description(将 date 转换成指定格式的字符串)
     *@date 2018/4/27 11:20
     *@param date, format
     *@return java.lang.String
     */
    public static String formatDate(Date date, String format){
        LocalDateTime localDateTime= LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);

        return localDateTime.format(formatter);
    }

    /**
     *@description(间隔天数)
     *@date 2018/6/4 17:42
     *@param
     *@return long
     */
    public static long betweenDays(Date start,Date end){
        LocalDateTime nowDateTime= LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime thirdDateTime= LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

        long between = ChronoUnit.DAYS.between(nowDateTime, thirdDateTime);
        return between;
    }


    /**
     *@Description(将带T 的字符串，转成 date类型)
     *@Date 2018/4/23
     *@Param [str]
     *@return java.util.Date
     * 请使用 org.apache.commons.lang3.time.FastDateFormat ,更加方便
     */
    @Deprecated
    public static Date parseDate(String str){
        TemporalAccessor date = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss").withZone(ZoneId.systemDefault()).parse(str);

        return Date.from(Instant.from(date));
    }
}
