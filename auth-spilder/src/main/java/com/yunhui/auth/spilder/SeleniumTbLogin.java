package com.yunhui.auth.spilder;
import com.yunhui.auth.Utils.CaptchaUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 无法登陆
 * 淘宝自动检测selenium
 */
public class SeleniumTbLogin {

    public static final String TB_LOGIN_URL="https://login.taobao.com/member/login.jhtml?style=mini_top";

    public static void main(String[] args) {

        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        ChromeOptions options=new ChromeOptions();

        List<String> list=new ArrayList<>();
        list.add("enable-automation");

        options.setExperimentalOption("excludeSwitches",list);

        WebDriver driver = new ChromeDriver(options);

        driver.get(TB_LOGIN_URL);

        SeleniumAliAuthLogin.input(driver,"17348930225","dsy68232731234");

        String nocaptchaSelect = "nocaptcha";

        String nocaptchaStartSelect = "nc_1_n1z";

        CaptchaUtils.dealNocaptcha(driver,By.id(nocaptchaSelect),By.id(nocaptchaStartSelect));

        driver.findElement(By.id("J_SubmitStatic")).click();

//        Set<Cookie> cookies = driver.manage().getCookies();
//
//        String cookie2=null;
//
//        for(Cookie cookie:cookies){
//            System.out.println("name:"+cookie.getName()+",value:"+cookie.getValue());
//            if(cookie.getName().equals("cookie2")){
//                cookie2=cookie.getValue();
//            }
//        }
//
//
//        String url="http://bar.tmall.com/cueAssetMsg.htm?sellerId=1943842398";
//
//        HttpGet httpGet = new HttpGet(url);
//
//        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");
//        httpGet.setHeader("Referer","https://login.taobao.com/member/login.jhtml");
//        httpGet.setHeader("Cookie","cookie2="+cookie2);
//
//        String result = HttpRequestUtil.doGet(url, httpGet);
//
//        System.out.println(result);

    }

}
