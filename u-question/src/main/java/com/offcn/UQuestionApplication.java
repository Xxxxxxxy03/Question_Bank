package com.offcn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(basePackages = {"com.offcn.question.dao"})
@EnableDiscoveryClient
public class UQuestionApplication {

    public static void main(String[] args) {
        SpringApplication.run(UQuestionApplication.class, args);
    }

}
