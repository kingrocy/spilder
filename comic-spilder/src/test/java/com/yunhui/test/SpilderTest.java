package com.yunhui.test;

import com.yunhui.comic.ComicApplication;
import com.yunhui.comic.bean.Detail;
import com.yunhui.comic.mapper.ChapterMapper;
import com.yunhui.comic.mapper.ComicMapper;
import com.yunhui.comic.mapper.DetailMapper;
import com.yunhui.comic.spilder.ChapterSpilder;
import com.yunhui.comic.spilder.ComicSpilder;
import com.yunhui.comic.spilder.Spilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-08 21:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ComicApplication.class)
@EnableAutoConfiguration
public class SpilderTest {

    @Autowired
    ComicMapper comicMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    DetailMapper detailMapper;

    @Test
    public void spilderComic(){
        ComicSpilder.spilderComic(comicMapper);
    }

    @Test
    public void spilderChapter(){
        ChapterSpilder.doSpilder(chapterMapper,comicMapper);
    }

}
