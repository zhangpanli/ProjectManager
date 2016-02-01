package com.self.projectmanager.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by 攀礼 on 2016/1/16.
 */
public class CollectionUtils {

    public static String join(Collection<?> collection) {
        if (isEmpty(collection)) {
            return "";
        }

        StringBuffer sb = new StringBuffer();

        Iterator<?> iterator = collection.iterator();

        sb.append(String.valueOf(iterator.next()));

        while (iterator.hasNext()) {
            sb.append(", ");
            sb.append(String.valueOf(iterator.next()));
        }

        return sb.toString();
    }

    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

}
