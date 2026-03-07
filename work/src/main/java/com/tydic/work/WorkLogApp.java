package com.tydic.work;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version 1.0
 * @date 2021/1/18 16:30
 */
@SpringBootApplication
@MapperScan("com.tydic.work.dao")
public class WorkLogApp {

    public static void main(String[] args) {
        SpringApplication.run(WorkLogApp.class);
    }
}
