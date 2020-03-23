package com.may.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderZK80Application {
    public static void main(String[] args) {
        SpringApplication.run(OrderZK80Application.class,args);
    }
}
