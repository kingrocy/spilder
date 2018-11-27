package com.yunhui.auth.spilder;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Title: AuthSpilder.java <br>
 * Description: <br>
 * Copyright (c) 聚阿网络科技版权所有 2018 <br>
 * Create DateTime: 2018年11月27日 15:41 <br>
 *
 * @author yun
 */
public class AuthSpilder {

    public static void main(String[] args) throws AWTException {

        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        String url = "https://oauth.taobao.com/authorize?response_type=token&client_id=24658522&state=1212&view=web";


        driver.get(url);

        driver.switchTo().frame(driver.findElement(By.id("J_loginIframe")));

        WebElement element = driver.findElement(By.id("J_Quick2Static"));

        element.click();

        WebElement userName = driver.findElement(By.id("TPL_username_1"));

        userName.sendKeys("17348930225");

        WebElement password = driver.findElement(By.id("TPL_password_1"));

        password.sendKeys("dsy68232731234");

        WebElement nocaptcha = driver.findElement(By.id("nocaptcha"));

        System.out.println(nocaptcha.isEnabled());

        System.out.println(nocaptcha.isDisplayed());

//        if(attribute.equals("display: block;")){
        Robot robot = new Robot();
        robot.setAutoDelay(800);
        robot.mouseMove(2555, 417);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(2819, 417);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//        }


        driver.findElement(By.id("J_SubmitStatic")).click();

    }
}
