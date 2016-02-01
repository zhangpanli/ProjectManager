package com.self.projectmanager.utils;

import com.self.projectmanager.exception.ToastException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 攀礼 on 2016/1/16.
 */
public class DateUtils {
    private static Map<String, DateFormat> formatMap = new HashMap<>();

    public static DateFormat getDateFormat(String format) {
        DateFormat df = formatMap.get(format);

        if (df == null) {
            df = new SimpleDateFormat(format);
            formatMap.put(format, df);
        }

        return df;
    }

    public static String format(Date date, String format) {
        return getDateFormat(format).format(date);
    }

    public static String format(Date date) {
        return format(date, "yyyy-MM-dd");
    }

    public static Date parse(String dateStr, String format) {
        try {
            return getDateFormat(format).parse(dateStr);
        } catch (ParseException e) {
            throw new ToastException(e);
        }
    }

    public static Date parse(String dateStr) {
        return parse(dateStr, "yyyy-MM-dd");
    }
}
