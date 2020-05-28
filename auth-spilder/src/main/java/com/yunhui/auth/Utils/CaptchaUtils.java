package com.yunhui.auth.Utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

/**
 * Title: CaptchaUtils.java <br>
 * Description: <br>
 * @author yun
 */
public class CaptchaUtils {

    public static final int LEN = 2;


    public static void dealNocaptcha(WebDriver driver, By nocaptchaSelect, By nocaptchaStartSelect){
        WebElement nocaptcha = driver.findElement(nocaptchaSelect);
        if (nocaptcha.isDisplayed()) {
            slide(driver, nocaptchaSelect, nocaptchaStartSelect);
        }
    }

    public static void slide(WebDriver driver, By nocaptcha, By start) {
        WebElement nocaptchaEle = driver.findElement(nocaptcha);
        int width = nocaptchaEle.getSize().getWidth();
        Actions action = new Actions(driver);
        WebElement startEle = driver.findElement(start);
        action.clickAndHold(startEle).perform();
        int k = width / LEN;
        for (int i = 1; i <= k + 1; i++) {
            if (i == k + 1) {
                action.moveByOffset(width - LEN * (i - 1), 0).perform();
            } else {
                action.moveByOffset(LEN, 0).perform();
            }
        }
        action.release();
    }


}
