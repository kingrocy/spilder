package com.yunhui.spilder.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * @Date : 2020/3/17 2:18 下午
 * @Author : dushaoyun
 */
public class MailSendUtils {

    private static final String USER_NAME = "xxx";
    private static final String PASSWORD = "xxx";
    private static final String SMTP_HOST = "smtp.mxhichina.com";
    private static final Integer SMTP_PORT = 25;
    private static final String SMTP_AUTH = "true";

    public static void sendEmail(String emailAddress, String subject, String content) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties javaMailProperties = new Properties();
        javaMailProperties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        javaMailProperties.setProperty("mail.smtp.socketFactory.port", "465");
        javaMailProperties.setProperty("mail.smtp.port", "465");


        mailSender.setJavaMailProperties(javaMailProperties);

        mailSender.setHost(SMTP_HOST);
        mailSender.setUsername(USER_NAME);
        mailSender.setPassword(PASSWORD);


        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailAddress);
        message.setFrom(USER_NAME);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

}
