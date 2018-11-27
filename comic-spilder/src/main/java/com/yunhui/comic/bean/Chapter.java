package com.yunhui.comic.bean;

import lombok.Data;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-01 21:02
 */
@Data
public class Chapter {

    private Long id;
    private Long article_id;
    private String cover;
    private Long category_id;
    private Integer price;
    private Boolean is_free;
    private String name;
    private String title;
    private String add_time;
    private Integer order;
    private Long origin_id;

}
