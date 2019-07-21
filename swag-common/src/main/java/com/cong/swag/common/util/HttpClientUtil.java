package com.cong.swag.common.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description HttpClient工具类
 * @Author zheng cong
 * @Date 2019-07-19
 */
public class HttpClientUtil extends StreamUtil {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String HTTP_CONTENT_CHARSET = Charset.defaultCharset().displayName().toLowerCase();

    public static HttpClient getHttpClient() {
        return HttpClientHolder.httpClient;
    }

    /**
     * 修改timeout配置, 传NULL的话, 使用默认值
     *
     * @param connectTimeout 连接超时时间 (毫秒) 默认5秒
     * @param poolTimeout 线程池获取连接超时时间 (毫秒) 默认10秒
     * @param socketTimeout 数据读取超时时间 (毫秒) 默认10秒
     * @param maxConn 最大连接数
     * @param maxPerRoute 单个Host最大连接数
     */

    public static void config(Integer connectTimeout, Integer poolTimeout, Integer socketTimeout, Integer maxConn,
        Integer maxPerRoute) {
        boolean valueChanged = false;
        if (connectTimeout != null && connectTimeout > 0) {
            if (connectTimeout.intValue() != HttpClientHolder.MAX_CONNECTION_TIME_OUT.intValue()) {
                HttpClientHolder.MAX_CONNECTION_TIME_OUT = connectTimeout;
                valueChanged = true;
            }
        }
        if (connectTimeout != null && connectTimeout > 0) {
            if (poolTimeout.intValue() != HttpClientHolder.MAX_CONNECTION_TIME_OUT.intValue()) {
                HttpClientHolder.MAX_CONNECTION_POOL_TIME_OUT = poolTimeout;
                valueChanged = true;
            }
        }
        if (connectTimeout != null && connectTimeout > 0) {
            if (socketTimeout.intValue() != HttpClientHolder.MAX_CONNECTION_POOL_TIME_OUT.intValue()) {
                HttpClientHolder.MAX_SOCKET_TIME_OUT = socketTimeout;
                valueChanged = true;
            }
        }
        if (connectTimeout != null && connectTimeout > 0) {
            if (maxConn.intValue() != HttpClientHolder.MAX_CONN.intValue()) {
                HttpClientHolder.MAX_CONN = maxConn;
                valueChanged = true;
            }
        }
        if (connectTimeout != null && connectTimeout > 0) {
            if (maxPerRoute.intValue() != HttpClientHolder.MAX_CONN_PER_ROUTE.intValue()) {
                HttpClientHolder.MAX_CONN_PER_ROUTE = maxPerRoute;
                valueChanged = true;
            }
        }
        if (valueChanged) {
            HttpClientHolder.init();
        }
    }

    public static String readStringFromStream(InputStream input, String charset) throws IOException {
        return StreamUtil.readStringFromStream(input, charset);
    }

    public static void releaseInputStream(InputStream inputStream) {
        StreamUtil.releaseInputStream(inputStream);
    }

    /**
     * please see HttpClientPostUtil
     */
    public static String sendPostRequest(String url, String postParam) {
        HttpPost post = new HttpPost(url);
        try {

            if (StringUtils.isNotEmpty(postParam)) {
                StringEntity entity = new StringEntity(postParam, "application/json", HTTP_CONTENT_CHARSET);
                post.setEntity(entity);
            }
            return executeMethodNew(post);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 通过get方法访问url
     *
     * @param charset 返回结果的charset
     */
    public static String getUrl(String url, Map<String, String> headers, String charset) {
        LOGGER.info("start to request {}", url);
        InputStream input = null;
        try {

            input = getInputStreamWithThrow(url, convertToHeaderList(headers));
            if (input == null) {
                throw new IOException("input is null");
            }
            return readStringFromStream(input, charset);
        } catch (IOException e) {
            LOGGER.error("", e);
            return null;
        }
    }

    /**
     * 通过get方法访问url，返回结果的charset为utf-8
     */
    public static String getUrl(String url) {
        return getUrl(url, Maps.newHashMap(), HTTP_CONTENT_CHARSET);
    }

    /**
     * post url
     *
     * @param url url to post
     * @param params kv params
     * @return ret string
     */
    public static String postUrl(String url, Map<String, String> params, Map<String, String> headers) {
        LOGGER.info("start to post url {}, params: {}", url, params);
        return postUrl(url, transferMapToNameValuePairs(params), convertToHeaderList(headers));
    }

    private static InputStream getInputStreamWithThrow(String url, List<Header> headers) throws IOException {
        HttpGet get = new HttpGet(url);
        if (CollectionUtils.isNotEmpty(headers)) {
            get.setHeaders(headers.toArray(new Header[headers.size()]));
        }
        try {
            HttpResponse response = getHttpClient().execute(get);
            InputStream is = response.getEntity().getContent();
            return is;
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * post url
     *
     * @param url 要访问的链接
     * @return post返回结果
     */
    private static String postUrl(String url, List<NameValuePair> nvps, List<Header> headers) {

        try {
            HttpPost httpPost = new HttpPost(url);

            if (CollectionUtils.isNotEmpty(nvps)) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(nvps, HTTP_CONTENT_CHARSET);
                httpPost.setEntity(entity);
            }
            if (CollectionUtils.isNotEmpty(headers)) {
                httpPost.setHeaders(headers.toArray(new Header[headers.size()]));
            }
            return executeMethodNew(httpPost);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * POST相关
     **/
    private static String executeMethodNew(HttpUriRequest method) throws IOException {
        InputStream input = null;
        try {
            input = getInputStream(method);
            if (input == null) {
                throw new IOException("input is null");
            }
            return readStringFromStream(input, HTTP_CONTENT_CHARSET);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } finally {
            releaseInputStream(input);
        }
    }

    private static InputStream getInputStream(HttpUriRequest method) throws IOException {
        HttpResponse response = getHttpClient().execute(method);
        InputStream is = response.getEntity().getContent();
        return is;
    }

    private static HttpResponse getHttpResponse(HttpUriRequest method) throws IOException {
        return getHttpClient().execute(method);
    }

    private static List<Header> convertToHeaderList(Map<String, String> headerMap) {
        List<Header> headers = Lists.newArrayList();
        if (headerMap == null) {
            return headers;
        }
        for (Entry<String, String> e : headerMap.entrySet()) {
            if (StringUtils.isEmpty(e.getKey())) {
                continue;
            }
            headers.add(new BasicHeader(e.getKey(), e.getValue()));
        }
        return headers;
    }

    private static List<NameValuePair> transferMapToNameValuePairs(Map<String, String> params) {
        List<NameValuePair> nvps = new ArrayList<>();
        if (params != null && !params.isEmpty()) {
            for (Entry<String, String> kv : params.entrySet()) {
                if (kv.getKey() != null && kv.getValue() != null) {
                    nvps.add(new BasicNameValuePair(kv.getKey(), kv.getValue()));
                }
            }
        }
        return nvps;
    }


    private static class HttpClientHolder {

        /**
         * user agent
         */
        private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36";
        public static CloseableHttpClient httpClient = null;
        /**
         * 全局最大连接数为500
         */
        private static Integer MAX_CONN = 500;
        /**
         * 单host最大连接数为50
         */
        private static Integer MAX_CONN_PER_ROUTE = 50;
        /**
         * 默认连接超时时间为 5秒
         */
        private static Integer MAX_CONNECTION_TIME_OUT = 5000;
        /**
         * 默认线程池获取连接超时时间为 10秒
         */
        private static Integer MAX_CONNECTION_POOL_TIME_OUT = 10000;
        /**
         * 默认数据读取超时时间为 10秒
         */
        private static Integer MAX_SOCKET_TIME_OUT = 10000;

        static {
            init();
        }

        private static void init() {
            RequestConfig config = RequestConfig
                .custom()
                .setConnectTimeout(MAX_CONNECTION_TIME_OUT)
                .setConnectionRequestTimeout(MAX_CONNECTION_POOL_TIME_OUT)
                .setMaxRedirects(4)
                .setSocketTimeout(MAX_SOCKET_TIME_OUT)
                .build();
            httpClient = HttpClientBuilder.create()
                .setMaxConnPerRoute(MAX_CONN_PER_ROUTE)
                .setMaxConnTotal(MAX_CONN)
                .setUserAgent(USER_AGENT)
                .setDefaultRequestConfig(config)
                .build();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (httpClient != null) {
                    Logger log = LoggerFactory.getLogger("ShutdownHook");
                    try {
                        log.info("Closing httpClient ...");
                        httpClient.close();
                    } catch (IOException e) {
                        log.error("Error on closing httpClient!");
                    }
                }
            }));

        }

    }

}
