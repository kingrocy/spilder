package com.yunhui.comic.utils;

import com.yunhui.utils.HttpRequestUtil;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

/**
 * Title: Test.java <br>
 * Description: <br>
 * Create DateTime: 2018年11月13日 13:38 <br>
 *
 * @author yun
 */
public class Test {

    public static void main(String[] args) {

        String url="https://bar.tmall.com/cueAssetMsg.htm?sellerId=3460735500";

        Cookie cookie = HttpRequestUtil.addCookie("cookie2", "116746ecdfd0d2a2349decd2478014eb", "tmall.com");

        CookieStore cookieStore=new BasicCookieStore();

        cookieStore.addCookie(cookie);

        String result = HttpRequestUtil.doGet(url, cookieStore);

        System.out.println(result);
    }
}
