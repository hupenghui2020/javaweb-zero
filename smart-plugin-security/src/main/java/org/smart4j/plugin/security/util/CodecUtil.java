package org.smart4j.plugin.security.util;

/**
 * @author hph
 */
public final class CodecUtil {

    public static String md5(String source) {

        return DigestUtils.md5Hex(source);
    }
}
