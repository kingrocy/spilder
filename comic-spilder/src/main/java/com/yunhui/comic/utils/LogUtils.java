package com.yunhui.comic.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-09-07 22:31
 */
public class LogUtils {

    private static final Logger SPILDER_LOGGER= LoggerFactory.getLogger("spilder-logger");

    private LogUtils(){}

    public static final Logger getSpilderLogger(){
        return SPILDER_LOGGER;
    }

}
