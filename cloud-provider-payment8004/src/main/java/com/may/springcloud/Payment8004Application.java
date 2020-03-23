package com.may.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //该注解用于,当使用consul或zookeeper作为注册中心时,向其中注册服务
public class Payment8004Application {
    public static void main(String[] args) {
        SpringApplication.run(Payment8004Application.class,args);
    }
}
