package com.offcn.uoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UOssApplication {

    public static void main(String[] args) {
        SpringApplication.run(UOssApplication.class, args);
    }

}
