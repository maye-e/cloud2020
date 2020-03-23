package com.may.springcloud.controller;

import com.may.springcloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@DefaultProperties(defaultFallback = "global_fallbackMethod") // 全局服务降级方法
@RestController
@Slf4j
@RequestMapping("/order/consumer/")
public class OrderHystrixController {

    @Resource
    private PaymentHystrixService paymentHystrixService;

    @GetMapping("payment/hystrix/ok/{id}")
    public String paymentInfo_ok(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_ok(id);
    }

    //虽然服务端可以配置服务降级,但一般会把它配置在客户端
    @HystrixCommand(fallbackMethod = "PaymentTimeOutFallbackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    @GetMapping("payment/hystrix/timeout/{id}")
    public String paymentInfo_timeout(@PathVariable("id") Integer id){
        //int a = 1/0;
        return paymentHystrixService.paymentInfo_timeout(id);
    }
    public String PaymentTimeOutFallbackMethod(@PathVariable("id") Integer id) {
        return "客户端,服务端1.5秒未返回或自己运行出错,我自动降级了，o(╥﹏╥)o";
    }

    @HystrixCommand //没有指定的降级方法,用全局的降级方法 global_fallbackMethod
    @GetMapping("payment/hystrix/global/{id}")
    public String paymentGlobalTimeout(@PathVariable("id") Integer id){
        int a = 1/0;
        return id.toString();
    }

    public String global_fallbackMethod(){
        return "全局降级处理信息，请稍后再试，o(╥﹏╥)o";
    }
}
