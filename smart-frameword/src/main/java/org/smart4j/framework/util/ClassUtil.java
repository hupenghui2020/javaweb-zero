package org.smart4j.framework.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

/**
 * 类操作工具类
 * 用于获取类加载器、加载类、获取指定包名下的所有类、某注解的类、实现了某接口的类、继承了谋父类的所有子类等
 * @author hph
 */
public final class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getClassLoader() {
        return null;
    }

    /**
     * 加载类
     * @param className
     * @param isInitialized
     * @return
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {

    }
}
