package com.may.loadRule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义负载均衡规则.默认轮询 RoundRobinRule
 * 注意:不能被 @component 注解扫描到,否则会被所有 ribbon 客户端共享
 */
@Configuration
public class CustomerLoadRule {

    @Bean
    public IRule myRule(){
//        return new RandomRule();//随机
        return new RoundRobinRule();//轮询(默认)
    }
}
