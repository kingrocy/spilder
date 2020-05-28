package com.yunhui.auth.spilder;
import com.yunhui.auth.Utils.CaptchaUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
/**
 * Title: SeleniumAlimmLogin.java <br>
 * Description: <br>
 * Create DateTime: 2018年12月03日 16:05 <br>
 *
 * @author yun
 */
public class SeleniumAliAuthLogin {


    public static final String AUTH_LOGIN_URL = "https://oauth.taobao.com/authorize?response_type=token&client_id=24658522&state=1212&view=web";

    public static final int LEN = 2;

    public static void main(String[] args) throws Exception {
        authLogin();
    }

    public static void authLogin() throws Exception {

        System.getProperties().setProperty("webdriver.chrome.driver",
                "auth-spilder/lib/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.get(AUTH_LOGIN_URL);

        driver.switchTo().frame(driver.findElement(By.id("J_loginIframe")));

        WebElement element = driver.findElement(By.id("J_Quick2Static"));

        element.click();

        input(driver, "1097598984@qq.com", "dsy6823273");

        String nocaptchaSelect = "nocaptcha";

        String nocaptchaStartSelect = "nc_1_n1z";

        CaptchaUtils.dealNocaptcha(driver,By.id(nocaptchaSelect),By.id(nocaptchaStartSelect));

        driver.findElement(By.id("J_SubmitStatic")).click();
    }


    public static void input(WebDriver driver, String userName, String passwd) {
        WebElement userNameEle = driver.findElement(By.id("TPL_username_1"));
        if (StringUtils.isEmpty(userNameEle.getText())) {
            userNameEle.sendKeys(userName);
        }
        WebElement passwordEle = driver.findElement(By.id("TPL_password_1"));
        if (StringUtils.isEmpty(passwordEle.getText())) {
            passwordEle.sendKeys(passwd);
        }
    }


}
