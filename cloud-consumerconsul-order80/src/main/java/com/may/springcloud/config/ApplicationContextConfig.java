package com.may.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced //生产者集群时,消费者需要开启此注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
