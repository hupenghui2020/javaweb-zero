package org.smart4j.framework.util;

/**
 * 数组工具类
 * @author hph
 */
public final class ArrayUtil {

    /**
     * 是否不为空
     * @param values
     * @return
     */
    public static boolean isNotEmpty(String[] values) {

        return values != null && values.length > 0;
    }
}
