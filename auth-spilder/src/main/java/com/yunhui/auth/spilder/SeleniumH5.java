package com.yunhui.auth.spilder;

import com.yunhui.auth.Utils.CaptchaUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: SeleniumH5.java <br>
 * Description: <br>
 * Create DateTime: 2018年12月04日 16:05 <br>
 *
 * @author yun
 */
public class SeleniumH5 {


    public static void main(String[] args) {
        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        options.addArguments("lang_zh_CN.UTF-8");

        //这步是关键哦，指定的浏览器size,对应手机型号的size
        options.addArguments("window-size=375,667");

        options.addArguments("user-agent=\"Mozilla/5.0 (iPhone; CPU iPhone OS 11_2 like Mac OS X) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0 Mobile/15C114 Safari/604.1\"");

        DesiredCapabilities cap = DesiredCapabilities.chrome();
        cap.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver = new ChromeDriver(options);

        driver.get("https://ai.m.taobao.com/index.html?pid=mm_130388949_42624180_261906911");

//      driver.findElement(By.id("J_MMREDBOX_TB_ID")).sendKeys("17348930225");

//      CaptchaUtils.dealNocaptcha(driver,By.id("nc_1-stage-1"), By.className("button"));


    }

}
