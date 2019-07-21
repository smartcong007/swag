package com.cong.swag.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @Author zheng cong
 * @Date 2019-07-19
 */
public class StreamUtil {

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(StreamUtil.class);
    private static final int BUFFER_SIZE = 1024;

    /**
     * 从input stream中读取string字符串
     *
     * @param input
     * @param charset
     * @return
     * @throws IOException
     */
    static String readStringFromStream(InputStream input, String charset) throws IOException {
        if (input == null) {
            return null;
        }
        try {
            ByteArrayOutputStream output = cloneInputStream(input);
            return new String(output.toByteArray(), charset);
        } finally {
            releaseInputStream(input);
        }
    }

    /**
     * 释放input stream(如果inputStream.close()方法已经被调用，4.x.x版本的httpclient不需要显式去consume content了)
     *
     * @param inputStream
     */
    static void releaseInputStream(InputStream inputStream) {
        if (inputStream == null) {
            return;
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            logger.error("Error on closing stream", e);
        }
    }

    static String readContentStringFromHttpResponse(HttpResponse response, String charset) {
        return readContentStringFromInputStream(readInputStreamFromHttpResponse(response), charset);
    }

    static String readContentStringFromInputStream(InputStream input, String charset) {
        try {
            return readStringFromStream(input, charset);
        } catch (IOException e) {
            logger.error("error on reading string from stream cause {}", e.toString());
            return null;
        } finally {
            releaseInputStream(input);
        }
    }

    static InputStream readInputStreamFromHttpResponse(HttpResponse response) {
        try {
            if (response != null) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return entity.getContent();
                } else {
                    logger.error("error on getting entity from response");
                    return null;
                }
            } else {
                logger.error("error on getting response");
                return null;
            }

        } catch (IOException e) {
            logger.error("error on reading stream from response cause {}\n{}", e.toString(), e.getCause().toString());
            return null;
        }
    }

    static ByteArrayOutputStream cloneInputStream(InputStream input) throws IOException {
        if(input == null) {
            throw new IOException("Failed to clone inputStream cause that is null");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = input.read(buffer)) > -1) {
            output.write(buffer, 0, len);
        }
        output.flush();
        return output;
    }

}
