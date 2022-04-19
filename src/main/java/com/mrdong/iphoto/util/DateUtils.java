package com.mrdong.iphoto.util;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_001 = "yyyy:MM:dd HH:mm:ss";



    /**
     * 时间转化成固定格式
     * @param format
     * @param date
     * @return
     */
    public static String pattern(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 按格式化转化成日期类型
     *
     * @param date
     * @param format
     * @return
     */
    public static Date getDate(String date, String format) {
        try {
            if (StringUtils.isEmpty(date)) {
                return null;
            }
            return (new SimpleDateFormat(format)).parse(date);
        } catch (ParseException var3) {
            return null;
        }
    }
}
