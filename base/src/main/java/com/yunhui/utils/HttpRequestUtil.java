package com.yunhui.utils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2017-11-30 9:49
 */
public class HttpRequestUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static String execute(CloseableHttpClient client, HttpUriRequest request) {
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity, DEFAULT_CHARSET);
                return jsonString;
            } else {
                logger.error("请求发生错误,state:"+state);
            }
        } catch (Exception e) {
            logger.error("请求出现异常:" + e.getClass().getName());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private static void execute(CloseableHttpClient httpClient,HttpUriRequest request,String filePath) {
        CloseableHttpResponse httpResponse = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try {
            httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                is = entity.getContent();
                File file = new File(filePath);
                fos = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成httppost对象
     *
     * @param url    url
     * @param params json格式的参数
     * @return
     */
    private static HttpPost generatHttpPost(String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity(params, DEFAULT_CHARSET);
        httpPost.setEntity(entity);
        return httpPost;
    }


    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) {
        return execute(HttpClients.createDefault(), generatHttpPost(url, params));
    }

    /**
     * 生成带cookie的httpclient
     * @param cookieStore
     * @return
     */
    public static CloseableHttpClient getHttpClient(CookieStore cookieStore){
        return HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
    }


    /**
     * get请求
     *
     * @param url url
     * @return
     */
    public static String doGet(String url, CookieStore cookieStore) {
        return execute(getHttpClient(cookieStore), new HttpGet(url));
    }

    /**
     * http for file download
     * @param url
     * @param cookieStore
     * @param filePath
     */
    public static void httpForFileDownload(String url, CookieStore cookieStore, String filePath) {
        execute(getHttpClient(cookieStore),new HttpGet(url),filePath);
    }

    /**
     * get请求
     *
     * @param url url
     * @return
     */
    public static String doGet(String url) {
        return execute(HttpClients.createDefault(),new HttpGet(url));
    }

    public static Cookie parseCookie(String cookieName, String cookieValue, String domain){
        BasicClientCookie cookie=new BasicClientCookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setPath("/");
        return cookie;
    }
}

