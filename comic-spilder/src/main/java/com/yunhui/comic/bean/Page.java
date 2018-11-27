package com.yunhui.comic.bean;

import lombok.Data;

import java.util.List;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 22:38
 */
@Data
public class Page<T> {

    private Boolean firstPage;
    private Boolean lastPage;
    private List<T> list;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalRow;
}
