package com.self.projectmanager.utils;

import com.self.projectmanager.exception.ToastException;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * Created by 攀礼 on 2016/1/16.
 */
public class BeanUtils {

    public static String getProperty(Object o, String propertyName) {
        Class<?> clazz = o.getClass();

        String methodName = getMethodName(propertyName);

        Object ret;
        try {
            Method m = clazz.getDeclaredMethod(methodName);
            ret = m.invoke(o);
        } catch (Exception e) {
            throw new ToastException(e);
        }

        if (ret != null && ret instanceof Date) {
            ret = DateUtils.format((Date) ret);
        }

        return String.valueOf(ret);
    }

    private static String getMethodName(String propertyName) {
        return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }
}
