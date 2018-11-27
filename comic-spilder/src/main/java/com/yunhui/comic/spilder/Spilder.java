package com.yunhui.comic.spilder;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunhui.comic.bean.Chapter;
import com.alibaba.fastjson.JSON;
import com.yunhui.comic.bean.Detail;
import com.yunhui.comic.common.Result;
import com.yunhui.comic.common.SpilderConstant;
import com.yunhui.comic.mapper.ChapterMapper;
import com.yunhui.comic.mapper.DetailMapper;
import com.yunhui.comic.utils.Download;
import com.yunhui.utils.HttpRequestUtil;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-01 21:00
 */
public class Spilder {

    private static final Logger logger = LoggerFactory.getLogger("spilder-logger");

    private static final String DOMAIN = "fqacc.com";


    private static final String SIGN_URL = "http://fqacc.com/api/sign";

    public static void main(String[] args) throws Exception {
        System.out.println(getJSessionId());
    }


    //总共15667章  一个线程爬200章 需要79个线程

    public static void doSpilder(int start, int end, ChapterMapper chapterMapper, DetailMapper detailMapper) throws Exception {

        ExecutorService service = Executors.newFixedThreadPool(end - start + 1);

        List<Future<String>> futures = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            Future<String> future = service.submit(new DetailRunnable(i, chapterMapper, detailMapper));
            futures.add(future);
        }

        for (Future future : futures) {
            logger.info(future.get().toString());
        }

    }

    public static List<String> spilderUrl(String url) throws Exception {

        List<String> imgs = new ArrayList<>();

        String content = null;

        int times = 0;

        while (content == null) {
            try {
                content = HttpRequestUtil.doGet(url, sign(getJSessionId()));
                if (content != null) {
                    System.out.println("spiler " + times + " success");
                    logger.info("spiler " + times + " success");
                }
            } catch (Exception e) {
                logger.error("spiler " + times + " error", e);
                times++;
            }
        }

        Document document = Jsoup.parse(content);

        Elements elements = document.select("#showcontentitem1").select("img");

        for (Element element : elements) {
            String img = element.attr("data-original");
            imgs.add(img);
        }

        return imgs;
    }


    /**
     * 随机生成一个用户 然后获取币
     *
     * @return
     */
    public static CookieStore sign(String jessionId) {
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie sessionCookie = new BasicClientCookie("JSESSIONID", jessionId);
        sessionCookie.setDomain(DOMAIN);
        sessionCookie.setPath("/");
        cookieStore.addCookie(sessionCookie);
        String content = HttpRequestUtil.doGet(SIGN_URL, cookieStore);
        Result result = JSON.parseObject(content, Result.class);
        if (result.getSucc() == true) {
            return cookieStore;
        }
        logger.error(result.getMsg());
        return null;
    }

    public static String getJSessionId() throws Exception {
        return operator(SpilderConstant.TRY_CONNECTION_URL);
    }


    public static String operator(String url) throws Exception {
        String sessionId = null;
        CookieStore cookieStore=new BasicCookieStore();
        CloseableHttpClient client=HttpRequestUtil.getHttpClient(cookieStore);
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Referer","http://fqacc.com/read/11000122");
        HttpResponse response = client.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statuscode = statusLine.getStatusCode();
        if (statuscode == 200) {
            List cookies = cookieStore.getCookies();
            for (int i = 0; i < cookies.size(); i++) {
                Cookie cookie = (Cookie) cookies.get(i);
                if ("JSESSIONID".equals(cookie.getName())) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }
}

class DetailRunnable implements Callable<String> {

    private Integer pageIndex;
    private Integer pageSize = 200;

    private ChapterMapper chapterMapper;
    private DetailMapper detailMapper;


    public DetailRunnable(Integer pageIndex, ChapterMapper chapterMapper, DetailMapper detailMapper) {
        this.pageIndex = pageIndex;
        this.chapterMapper = chapterMapper;
        this.detailMapper = detailMapper;
    }

    @Override
    public String call() {

        List<Chapter> chapters = chapterMapper.selectPage(new Page<Chapter>(pageIndex, pageSize), null);

        for (Chapter chapter : chapters) {
            try {
                List<String> list = Spilder.spilderUrl(SpilderConstant.CHAPTER_CONTENT_URL + chapter.getOrigin_id());
                Detail detail = new Detail();
                detail.setChapter_id(chapter.getId());
                detail.setChapter_name(chapter.getName());
                detail.setImg_urls(list.stream().collect(Collectors.joining(",")));
                detailMapper.insert(detail);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "thread-" + pageIndex + "完成任务";

    }

}


