package com.yunhui.comic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @Author: Yun
 * @Description:
 * @Date: Created in 2018-08-03 16:54
 */
@SpringBootApplication
public class ComicApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ComicApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ComicApplication.class);
    }

}
