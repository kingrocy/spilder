package com.yunhui.auth.spilder;

import com.alibaba.fastjson.JSON;
import com.yunhui.utils.HttpRequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Title: Test.java <br>
 * Description: <br>
 * Create DateTime: 2018年12月14日 10:11 <br>
 *
 * @author yun
 */
public class Test {

    private static final String TB_ITEM_IMAGE_DESC_URL = "https://h5api.m.taobao.com/h5/mtop.taobao.detail.getdesc/6.0/?data=%7B%22id%22%3A%221111%22%2C%22type%22%3A%221%22%2C%22f%22%3A%22TB1OheADXzqK1RjSZFv8qwB7Vla%22%7D";
    public static final String HTTPS_HEAD = "https:";

    public static final String HTTP = "http";

    public static void main(String[] args) {

        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        ChromeOptions chromeOptions = new ChromeOptions();

        chromeOptions.addArguments("--headless");

        WebDriver driver = new ChromeDriver(chromeOptions);

        List<String> itemDetailImgList = new ArrayList<>();

        driver.get(TB_ITEM_IMAGE_DESC_URL.replace("1111","554310245035"));

        String content = driver.findElement(By.tagName("body")).getText();

        System.out.println(content);


        if (StringUtils.isNotEmpty(content)) {
            String dom = JSON.parseObject(content).getJSONObject("data").get("pcDescContent").toString();
            Document document = Jsoup.parse(dom);
            Elements elements = document.select("img");
            for (Element element : elements) {
                String imgUrl = element.attr("src");
                if (StringUtils.isEmpty(imgUrl)) {
                    continue;
                }
                if (judgeIsAImage(imgUrl)) {
                    if (imgUrl.startsWith(HTTP)) {
                        itemDetailImgList.add(imgUrl);
                    } else {
                        itemDetailImgList.add(HTTPS_HEAD + imgUrl);
                    }
                }
            }
        }

        for (String s : itemDetailImgList) {
            System.out.println(s);
        }
    }


    public static Boolean judgeIsAImage(String imageUrl) {
        if (StringUtils.isEmpty(imageUrl)) {
            return false;
        }
        String[] strs = new String[]{".jpg", ".png", ".jpeg", ".gif"};
        for (String str : strs) {
            if (imageUrl.endsWith(str)) {
                return true;
            }
        }
        return false;
    }

}
