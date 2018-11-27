package com.yunhui.comic.controller;

import com.yunhui.comic.mapper.ChapterMapper;
import com.yunhui.comic.mapper.ComicMapper;
import com.yunhui.comic.mapper.DetailMapper;
import com.yunhui.comic.spilder.Spilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-08 23:47
 */
@RestController
public class CoreController {

    @Autowired
    ComicMapper comicMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    DetailMapper detailMapper;

    @GetMapping("/spilder")
    public void spilder(int start,int end) {
        try {
            Spilder.doSpilder(start,end,chapterMapper,detailMapper);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
