package com.yunhui.comic.bean;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.Data;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 22:00
 */
@Data
public class Comic {

    @TableId
    private Long id;
    /**
     * 作者
     */
    private String authors_name;

    /**
     * 分类名称
     */
    private String base_category_name;

    /**
     * 封面图片
     */
    private String cover_large;

    /**
     * 是否完结
     */
    private Boolean is_done;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 最新章节
     */
    private String last_chapter_title;

    /**
     * 简介
     */
    private String remark;

    /**
     * 标题
     */
    private String title;

    private Long origin_id;

    /**
     * 点击数排序
     */
    private Integer click;

    /**
     * 更新时间排序
     */
    private Integer date;

}
