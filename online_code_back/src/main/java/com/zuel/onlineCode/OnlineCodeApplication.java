package com.zuel.onlineCode;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

//启动类
@SpringBootApplication
@MapperScan("com.zuel.onlineCode.mapper")
@EnableCaching//开启缓存功能
public class OnlineCodeApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(OnlineCodeApplication.class, args);

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OnlineCodeApplication.class);
    }
}
