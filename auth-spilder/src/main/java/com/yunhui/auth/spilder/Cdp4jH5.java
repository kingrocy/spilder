package com.yunhui.auth.spilder;

import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.event.InputEvent;


/**
 * Title: Cdp4jH5.java <br>
 * Description: <br>
 *
 * @author yun
 */
public class Cdp4jH5 {

    private static SessionFactory factory;

    private static final String INPUT_SELECT = "#J_MMREDBOX_TB_ID";

    private static final String SLIDE_SELECT = "#nc_1-stage-1";

    private static final String HB_SELECT = "#hb";

    private static final String URL = "https://ai.m.taobao.com/index.html?pid=mm_130388949_42624180_261906911";

    static {
        Launcher launcher = new Launcher();
        factory = launcher.launch();
    }

    public static void main(String[] args) throws Exception {
        String[] strs = new String[]{"18158171066", "15502233614"};
        for (String str : strs) {
            spilder(str);
        }
    }

    public static Session getSession() {
        Session session = factory.create();
        session.setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 11_2 like Mac OS X) AppleWebKit/604.4.7 (KHTML, like Gecko) Version/11.0 Mobile/15C114 Safari/604.1");
        return session;
    }


    public static void spilder(String account) throws Exception {
        Session session = getSession();
        session.navigate(URL).waitDocumentReady();
        clickHb(session);
        input(session, account);
        slide(session);
        session.clearCache();
        session.close();
    }


    public static String getInputValue(Session session) {
        return session.getProperty(INPUT_SELECT, "value").toString();
    }

    public static void setInputValue(Session session, String value) {
        session.selectInputText(INPUT_SELECT).sendKeys(value);
    }


    public static void input(Session session, String value) {
        if (StringUtils.isNotEmpty(getInputValue(session))) {
            setInputValue(session, "");
        }
        setInputValue(session, value);
    }

    public static void slide(Session session) throws AWTException {
        while (session.getAttribute(SLIDE_SELECT, "style") != null && !session.getAttribute(SLIDE_SELECT, "style").contains("none")) {
            Robot robot = new Robot();
            robot.setAutoDelay(800);
            robot.mouseMove(885, 715);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseMove(1030, 715);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            session.wait(500);
        }
    }

    public static void clickHb(Session session) {
        while (true) {
            try {
                getInputValue(session);
                break;
            } catch (Exception e) {
                session.wait(500);
                session.click(HB_SELECT);
                session.wait(500);
            }
        }
    }

}
