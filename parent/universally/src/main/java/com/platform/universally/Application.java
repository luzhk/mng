package com.platform.universally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.support.SpringBootServletInitializer;
//import org.springframework.web.WebApplicationInitializer;

/**
 * 创建springboot启动
 * Created by Administrator on 2018/8/5.
 */
@SpringBootApplication
public class Application extends SpringApplication/* SpringBootServletInitializer implements WebApplicationInitializer*/ {

    /*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }*/

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
