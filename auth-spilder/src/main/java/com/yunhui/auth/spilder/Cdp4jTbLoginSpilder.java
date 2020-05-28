package com.yunhui.auth.spilder;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import org.apache.commons.lang3.StringUtils;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-06-25 10:18
 */
public class Cdp4jTbLoginSpilder {


    public static void main(String[] args) throws Exception {
        login("1097598984@qq.com","dsy6823273");
    }


    /**
     * 模拟淘宝登陆
     * @throws Exception
     */
    public static void login(String username,String password) throws Exception {

        Launcher launcher = new Launcher();

        SessionFactory factory = launcher.launch();

        Session session = factory.create();

        String url = "https://login.taobao.com/member/login.jhtml?style=mini_top";

        session.navigate(url).waitDocumentReady();


        //判断J_LoginBox这个登录div是否存在作为登录是否成功的标准
        String loginDivClass = session.getAttribute("#J_LoginBox", "class");

        while (StringUtils.isNotEmpty(loginDivClass)) {

            if (StringUtils.isNotEmpty(session.getProperty("#TPL_username_1", "value").toString())) {
                session.setProperty("#TPL_username_1","value","");
            }

            session.wait(800);
            session.click("#TPL_username_1").sendKeys(username);

            if (StringUtils.isNotEmpty(session.getProperty("#TPL_password_1", "value").toString())) {
                session.setProperty("#TPL_password_1","value","");
            }
            session.wait(800);

            session.click("#TPL_password_1").sendKeys(password);

            //判断有没有滑块
            if(session.getAttribute("#nocaptcha","style")!=null&&!session.getAttribute("#nocaptcha","style").contains("none")) {

                //TODO 因为滑块区域固定 可以随机取x y坐标 然后不停的切换坐标尝试点击
                Robot robot = new Robot();
                robot.setAutoDelay(800);
                robot.mouseMove(900, 266);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseMove(1060, 266);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }

            session.click("#J_SubmitStatic");

            session.wait(1000);

            try {
                loginDivClass = session.getAttribute("#J_LoginBox", "class");
            }catch (Exception e){
                loginDivClass=null;
                e.printStackTrace();
            }
        }
    }

}

