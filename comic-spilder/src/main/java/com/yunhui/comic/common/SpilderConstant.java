package com.yunhui.comic.common;
/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-08 22:21
 */
public class SpilderConstant {

    public static final String DOMAIN="http://fqacc.com";

    public static final String IMG_DOMAIN="http://img.9icph.cn";

    /**
     * 章节爬取链接  第一个参数是漫画ID  第二个参数是页码 第三个参数是每页展示数量
     */
    public static final String CHAPTER_SPILDER_URL="http://fqacc.com/api/chapter_list/%s-1-%s-%S";


    public static final String NO_IMG_SHOW="/static/xmh/theme/images/no_img.png";

    public static final String CHAPTER_CONTENT_URL="http://fqacc.com/read/";

    public static final String TRY_CONNECTION_URL="http://fqacc.com/user/task";

    /**
     * 漫画列表爬取 ---按照更新时间排序
     */
    public static final String COMIC_SPILDER_URL_DATE="http://fqacc.com/api/cate/1-0-2-1-%s";

    /**
     * 漫画列表爬取---根据点击量
     */
    public static final String COMIC_SPILDER_URL_CLICK="http://fqacc.com/api/cate/1-0-2-2-%s";


}
