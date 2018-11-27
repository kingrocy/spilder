package com.yunhui.comic.spilder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yunhui.comic.bean.Chapter;
import com.yunhui.comic.bean.Comic;
import com.yunhui.comic.bean.Page;
import com.yunhui.comic.common.Result;
import com.yunhui.comic.common.SpilderConstant;
import com.yunhui.comic.mapper.ChapterMapper;
import com.yunhui.comic.mapper.ComicMapper;
import com.yunhui.utils.HttpRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 22:22
 */
public class ComicSpilder {

    private static final Logger LOGGER= LoggerFactory.getLogger(ComicSpilder.class);

    private static final Integer TOTAL_PAGE;

    static {
        TOTAL_PAGE=getTotalPage();
    }


    public static void main(String[] args) {
//        spilderComic();
    }

    public static void spilderComic(ComicMapper comicMapper){

        int sort=0;
        LOGGER.info("totalPage:"+TOTAL_PAGE);
        //从第一页开始爬
        for(int page=1;page<=TOTAL_PAGE;page++){
            Page<Comic> comicPage = doSpilder(String.format(SpilderConstant.COMIC_SPILDER_URL_CLICK, page));
            if(comicPage!=null){
                if(comicPage.getList()!=null){
                    LOGGER.info("page:{},size:{}",page,comicPage.getList().size());
                    for(int i=0;i<comicPage.getList().size();i++){
                        Comic comic=comicPage.getList().get(i);
                        if(comic.getCover_large().contains("http")){
                            comic.setCover_large(comic.getCover_large());
                        }else{
                            comic.setCover_large(SpilderConstant.IMG_DOMAIN+comic.getCover_large());
                        }
                        comic.setOrigin_id(Long.valueOf(comic.getId()));
                        comic.setClick(sort);
                        comicMapper.insertAllColumn(comic);
                        sort++;
                    }
                }
            }
        }
    }


    public static Integer getTotalPage(){
        Page<Comic> page = doSpilder(String.format(SpilderConstant.COMIC_SPILDER_URL_CLICK, 1));

        if(page==null){
            System.out.println("getTotalPage error..");
        }
        return page.getTotalPage();
    }

    public static Page<Comic> doSpilder(String url){

        String content = HttpRequestUtil.doGet(url);

        Result result = JSON.parseObject(content, new TypeReference<Result<Comic>>(){});


        if(result.getSucc()){


            Page<Comic> page = result.getResult();

            System.out.println("spilder success...");

            return page;

        }



        System.out.println("doSpilder fail,result="+result);

        return null;
    }

}
