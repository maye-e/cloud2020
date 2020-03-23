package com.may.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients //激活Feign注解. feign集成了ribbon,自带负载均衡
public class OrderFeign80Application {

    public static void main(String[] args) {
        SpringApplication.run(OrderFeign80Application.class,args);
    }
}
