/*
package com.hph.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

*/
/**
 * 属性文件工具类
 * @author hph
 *//*

public final class PropsUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropsUtil.class);

    public static Properties loadProps(String fileName) {
        Properties props = null;
        InputStream is = null;
        try{
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
            if(is == null) {
                throw new FileNotFoundException(fileName + "file is not found");
            }
            props = new Properties();
            props.load(is);
        }catch (IOException e){
            LOGGER.error("load properties file failure", e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOGGER.error("close input stream failure", e);
                }
            }
        }
        return props;
    }

    */
/**
     * 获取字符型属性
     * @param props
     * @param key
     * @return
     *//*

    public static String getString(Properties props, String key) {
        return getString(props, key, "");
    }

    */
/**
     * 获取字符型属性（默认值为空字符串）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     *//*

    private static String getString(Properties props, String key, String defaultValue) {

        String value = defaultValue;
        if(props.containsKey(key)) {
            value = props.getProperty(key);
        }
        return value;
    }

    */
/**
     * 获取数值型属性（默认值为 0）
     * @param props
     * @param key
     * @return
     *//*

    public static int getInt(Properties props, String key) {
        return getInt(props, key, 0);
    }

    */
/**
     * 获取数值型属性（可指定默认值）
     * @param props
     * @param key
     * @param defaultValue
     * @return
     *//*

    private static int getInt(Properties props, String key, int defaultValue) {
        String value = defaultValue;
        if(props.containsKey(key)) {
            value =  props.getProperty(key);
        }
        return value;
    }
}
*/
