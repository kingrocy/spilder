package com.yunhui.auth.spilder;

import com.yunhui.auth.Utils.CaptchaUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Set;

/**
 * Title: SeleniumPubTbLogin.java <br>
 * Description: <br>
 * Create DateTime: 2018年12月04日 9:45 <br>
 *
 * @author yun
 */
public class SeleniumPubTbLogin {


    public static final String PUB_LOGIN_URL="https://www.alimama.com/member/login.htm?forward=http%3A%2F%2Fpub.alimama.com%2Fmanage%2Feffect%2Foverview.htm";

    public static void main(String[] args) {

        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get(PUB_LOGIN_URL);

        driver.switchTo().frame(driver.findElement(By.name("taobaoLoginIfr")));

        WebElement element = driver.findElement(By.id("J_Quick2Static"));

        element.click();

        SeleniumAliAuthLogin.input(driver,"Aipi小马达","uIXx9Doe");

        String nocaptchaSelect = "nocaptcha";

        String nocaptchaStartSelect = "nc_1_n1z";

        CaptchaUtils.dealNocaptcha(driver,By.id(nocaptchaSelect),By.id(nocaptchaStartSelect));

    }

}
