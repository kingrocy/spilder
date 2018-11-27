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

import java.util.List;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-08 22:38
 */
public class ChapterSpilder {

    public static void doSpilder(ChapterMapper chapterMapper, ComicMapper comicMapper) {

        List<Comic> comics = comicMapper.selectList(null);

        for (Comic comic : comics) {
            spilderChapter(chapterMapper,comic.getOrigin_id());
        }


    }

    public static void spilderChapter(ChapterMapper chapterMapper, Long comicId) {

        String content = HttpRequestUtil.doGet(String.format(SpilderConstant.CHAPTER_SPILDER_URL, comicId, 1, Integer.MAX_VALUE));

        Result<Chapter> chapterResult = JSON.parseObject(content, new TypeReference<Result<Chapter>>() {
        });

        if (chapterResult != null) {
            if (chapterResult.getSucc()) {
                Page<Chapter> chapterPage = chapterResult.getResult();
                for (Chapter chapter : chapterPage.getList()) {
                    chapter.setCover(SpilderConstant.IMG_DOMAIN + (chapter.getCover()==null?SpilderConstant.NO_IMG_SHOW:chapter.getCover()));
                    chapter.setOrigin_id(chapter.getId());
                    chapterMapper.insert(chapter);
                }
                System.out.println("spilder success ..");
            }
        }else{
            System.out.println("spilder error..");
        }

    }

}
