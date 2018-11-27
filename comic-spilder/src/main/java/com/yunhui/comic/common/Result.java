package com.yunhui.comic.common;

import com.yunhui.comic.bean.Page;
import lombok.Data;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 14:26
 */
@Data
public class Result<T> {

    private String msg;
    private Page<T> result;
    private Integer code;
    private Boolean succ;

}
