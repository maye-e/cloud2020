package com.may.springcloud.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel(){
        /*
          feign的日志级别:
            NONE: 默认,不显示任何日志
            BASIC: 仅记录方法请求、URL、响应状态码以及执行时间
            HEADERS: 除了BASIC中定义的信息外,还有请求和响应的头信息
            FULL: 除了HEADERS中定义的信息外,还有请求和响应的正文及元数据
         */
        return Logger.Level.FULL;
    }
}
