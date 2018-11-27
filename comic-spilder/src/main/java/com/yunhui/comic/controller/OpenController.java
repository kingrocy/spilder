package com.yunhui.comic.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunhui.comic.bean.Chapter;
import com.yunhui.comic.bean.Comic;
import com.yunhui.comic.bean.Detail;
import com.yunhui.comic.bean.Page;
import com.yunhui.comic.common.Result;
import com.yunhui.comic.mapper.ChapterMapper;
import com.yunhui.comic.mapper.ComicMapper;
import com.yunhui.comic.mapper.DetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-09 17:18
 */
@RestController
@CrossOrigin
public class OpenController {

    @Autowired
    ComicMapper comicMapper;

    @Autowired
    ChapterMapper chapterMapper;

    @Autowired
    DetailMapper detailMapper;

    @GetMapping("/detail/{id}")
    public Detail getDetail(@PathVariable("id") Long id){
        Detail detail=new Detail();
        detail.setChapter_id(id);
        return detailMapper.selectOne(detail);
    }


    @GetMapping("/chapterList")
    public Result<Chapter> chapterList(@RequestParam(value = "pageIndex",defaultValue = "1")Integer pageIndex,
                                      @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                     @RequestParam(value = "id")Long id){

        com.baomidou.mybatisplus.plugins.Page<Comic> page=new com.baomidou.mybatisplus.plugins.Page<>(pageIndex,pageSize);

        List<Chapter> chapters = chapterMapper.selectPage(page, new EntityWrapper<Chapter>().eq("article_id", id).orderBy("`order`", true));

        Page<Chapter> chapterPage=new Page<>();

        chapterPage.setPageNumber(pageIndex);
        chapterPage.setPageSize(pageSize);
        chapterPage.setList(chapters);
        chapterPage.setTotalRow((int) page.getTotal());
        chapterPage.setTotalPage((int) page.getPages());
        Result<Chapter> result=new Result<>();
        result.setResult(chapterPage);
        result.setSucc(true);

        return result;
    }

    @GetMapping("/comic/{id}")
    public Comic getComic(@PathVariable("id")Long id){
        Comic comic=new Comic();
        comic.setOrigin_id(id);
       return comicMapper.selectOne(comic);
    }


    @GetMapping("/comicList")
    public Result<Comic> getComicList(@RequestParam(value = "pageIndex",defaultValue = "1")Integer pageIndex,
                                            @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){

        com.baomidou.mybatisplus.plugins.Page<Comic> page=new com.baomidou.mybatisplus.plugins.Page<>(pageIndex,pageSize);

        List<Comic> comics = comicMapper.selectPage(page, new EntityWrapper<Comic>().orderBy("click",true));

        Page<Comic> comicPage=new Page<>();

        comicPage.setPageNumber(pageIndex);
        comicPage.setPageSize(pageSize);
        comicPage.setList(comics);
        comicPage.setTotalRow((int) page.getTotal());
        comicPage.setTotalPage((int) page.getPages());

        Result<Comic> result=new Result<>();
        result.setResult(comicPage);
        result.setSucc(true);

        return result;

    }

}
