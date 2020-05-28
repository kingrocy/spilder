package com.yunhui.auth.spilder;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * @Date : 2020/1/10 4:36 下午
 * @Author : dushaoyun
 *
 */
public class Test2 {

    public static void main(String[] args) throws AWTException, InterruptedException {
        while (true){
            Robot robot = new Robot();
            robot.setAutoDelay(100);
            robot.mouseMove(1100, 700);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
//            Thread.sleep(2000);
        }

    }
}
