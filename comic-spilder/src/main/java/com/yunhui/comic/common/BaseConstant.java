package com.yunhui.comic.common;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 15:23
 */
public class BaseConstant {

    public static final String IMG_DOWNLOAD_PATH="d:/comic1/";


    public static void main(String[] args) {

        String url="http://xmh-resc.9icph.cn/static/xmh/comics/100054/10004401/11_e583a60d9c45001f.jpg";


        String[] strings = url.split("/");

        System.out.println(strings[strings.length-1].split("_")[0]);


    }
}
