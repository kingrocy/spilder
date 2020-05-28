package com.yunhui.spilder.init;

import com.alibaba.fastjson.JSON;
import com.yunhui.spilder.bean.TitleItem;
import com.yunhui.spilder.utils.MailSendUtils;
import io.webfolder.cdp.Launcher;
import io.webfolder.cdp.session.Session;
import io.webfolder.cdp.session.SessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Date : 2020/5/14 3:12 下午
 * @Author : dushaoyun
 */
@Component
@Slf4j
public class Init {

    private Launcher launcher;

    private SessionFactory factory;

    private List<TitleItem> items;

    private static final List<String> email_address = new ArrayList() {{
        add("hnk6651@dingtalk.com");
    }};

    @PostConstruct
    public void init() {
        launcher = new Launcher();
        ArrayList<String> arguments = new ArrayList<>();
        //如果添加此行就不会弹出google浏览器
        arguments.add("--headless");
        arguments.add("--no-sandbox");
        factory = launcher.launch("/usr/bin/google-chrome-stable", arguments);
        new Thread(() -> {
            while (true) {

                Session session = factory.create();

                String url = "http://mba.zju.edu.cn/lists-zsdt.html?page=1";

                session.navigate(url).waitDocumentReady();

                String content = session.getContent();

                Document document = Jsoup.parse(content);

                Element new_message = document.getElementById("new_message");

                Elements lis = new_message.getElementsByTag("li");

                List<TitleItem> titleItems = new ArrayList<>();

                for (Element element : lis) {
                    Element a = element.getElementsByTag("a").first();
                    String title = a.attr("title");
                    String href = a.attr("href");
                    if (title.contains("MBA") && title.contains("录取") && title.contains("全日制")) {
                        titleItems.add(new TitleItem(title, href));
                    }
                }

                log.info("titleItems:{}", JSON.toJSONString(titleItems));

                if (CollectionUtils.isEmpty(items)) {
                    items = new ArrayList<>(titleItems);
                    sendEmail(titleItems);
                } else {
                    if (items.size() != titleItems.size()) {
                        sendEmail(titleItems);
                    } else {
                        boolean eq = true;
                        for (int i = 0; i < items.size(); i++) {
                            if (!items.get(i).equals(titleItems.get(i))) {
                                eq = false;
                            }
                        }
                        if (!eq) {
                            sendEmail(titleItems);
                        }
                    }
                }

                session.close();

                try {
                    Thread.sleep(5 * 60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void sendEmail(List<TitleItem> titleItems) {
        StringBuilder sb = new StringBuilder();
        sb.append("Hello,这里有【浙大MBA录取通知】相关的最新消息，请及时查阅。\n");
        for (TitleItem titleItem : titleItems) {
            sb.append("标题：").append(titleItem.getTitle()).append(",链接：").append(titleItem.getLink()).append("\n");
        }
        for (String emailAddress : email_address) {
            MailSendUtils.sendEmail(emailAddress, "浙大MBA录取通知最新消息", sb.toString());
        }
    }

}
