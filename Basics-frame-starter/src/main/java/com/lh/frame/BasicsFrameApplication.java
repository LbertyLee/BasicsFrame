package com.lh.frame;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lh")
@EnableCaching
@MapperScan("com.lh.frame.**.dao")
public class BasicsFrameApplication {
    public static void main(String[] args) {
        SpringApplication.run(BasicsFrameApplication.class);
        System.out.println("@@@BasicsFrame启动成功@@@");
    }
}