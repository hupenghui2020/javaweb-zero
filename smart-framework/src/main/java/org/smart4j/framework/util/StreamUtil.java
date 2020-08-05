package org.smart4j.framework.util;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * 流操作工具类
 * @author 10499
 */
public final class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 从输入流中获取字符串
     * @param is
     * @return
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception e) {
            LOGGER.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    /**
     * 将输入流复制到输出流
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer = new byte[4 * 1024];
            while((length = inputStream.read(buffer, 0, buffer.length)) != -1 ) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        }catch (Exception e) {
            LOGGER.error("copy stream failure", e);
            throw new RuntimeException(e);
        }finally {
            try {
                inputStream.close();
                outputStream.close();
            }catch (Exception e) {
                LOGGER.error("close stream failure", e);
            }
        }
    }
}
