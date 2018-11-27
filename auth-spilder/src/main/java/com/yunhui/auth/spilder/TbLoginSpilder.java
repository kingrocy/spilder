package com.yunhui.auth.spilder;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-06-25 10:18
 */
public class TbLoginSpilder {

    /**
     * 模拟淘宝登陆
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {

        Launcher launcher = new Launcher();

        SessionFactory factory = launcher.launch();

        Session session = factory.create();

        String url = "https://login.taobao.com/member/login.jhtml?style=mini_top";

        session.navigate(url)
                .waitDocumentReady();


        //判断J_LoginBox这个登录div是否存在作为登录是否成功的标准
        String loginDivClass = session.getAttribute("#J_LoginBox", "class");

        while (StringUtils.isNotEmpty(loginDivClass)) {

            if (StringUtils.isEmpty(session.getProperty("#TPL_username_1", "value").toString())) {
                session.click("#TPL_username_1")
                        .sendKeys("杜少云1234");
            }

            session.click("#TPL_password_1")
                    .sendKeys("dsy68232731234");



            //判断有没有滑块
            if(session.getAttribute("#nocaptcha","style")!=null&&!session.getAttribute("#nocaptcha","style").contains("none")) {

                Robot robot = new Robot();
                robot.setAutoDelay(800);
                robot.mouseMove(882, 266);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseMove(1098, 266);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }

            session.click("#J_SubmitStatic");

            session.wait(1000);

            loginDivClass = session.getAttribute("#J_LoginBox", "class");
        }

    }

    //TODO 滑块有的时候可以成功 有时会失败  x y坐标定位不准确

}

