package com.yunhui.auth.spilder;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import org.apache.commons.lang3.StringUtils;
import java.awt.*;
import java.awt.event.InputEvent;

/**
 * 淘宝自动授权
 //TODO 无法实现 cdp4j无法切换iframe 检测不到iframe里的元素
 */
public class AuthSpilder {

    public static final String AUTH_LOGIN_URL = "https://oauth.taobao.com/authorize?response_type=token&client_id=24658522&state=1212&view=web";


    public static void main(String[] args) throws Exception {

        Launcher launcher = new Launcher();

        SessionFactory factory = launcher.launch();

        Session session = factory.create();

        session.navigate(AUTH_LOGIN_URL)
                .waitDocumentReady();

//        //点击切换iframe
//        String flag="";
//
//        try {
//            session.getProperty("TPL_username_1","value").toString();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        while (StringUtils.isEmpty(flag)){
//
//            Robot robot=new Robot();
//
//            robot.setAutoDelay(800);
//
//            robot.mouseMove(910,185);
//
//            robot.mousePress(InputEvent.BUTTON1_MASK);
//
//            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//
//            try {
//                flag=session.getProperty("TPL_username_1", "value").toString();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//            System.out.println(flag);
//        }

        String location = session.getLocation();

        System.out.println(location);

        while (!location.startsWith("https://oauth.taobao.com")){

            if (StringUtils.isEmpty(session.getProperty("#TPL_username_1", "value").toString())) {
                session.click("#TPL_username_1")
                        .sendKeys("17348930225");
            }

            session.click("#TPL_password_1")
                    .sendKeys("dsy68232731234");

            //判断有没有滑块
            if(session.getAttribute("#nocaptcha","style")!=null&&!session.getAttribute("#nocaptcha","style").contains("none")) {
                Robot robot = new Robot();
                robot.setAutoDelay(800);
                robot.mouseMove(910, 260);
                robot.mousePress(InputEvent.BUTTON1_MASK);
                robot.mouseMove(1098, 260);
                robot.mouseRelease(InputEvent.BUTTON1_MASK);
            }

            session.click("#J_SubmitStatic");

            session.wait(1000);

            location = session.getLocation();

        }

    }


}
