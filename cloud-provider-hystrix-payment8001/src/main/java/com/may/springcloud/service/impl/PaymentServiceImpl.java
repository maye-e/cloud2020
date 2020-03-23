package com.may.springcloud.service.impl;

import cn.hutool.core.util.IdUtil;
import com.may.springcloud.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    //----------------------------------- 服务降级 -------------------------------------

    @Override
    public String paymentInfo_ok(Integer id) {
        return "线程池:\t" + Thread.currentThread().getName() + "\tpaymentInfo_ok,id:\t" + id;
    }

    @Override
    //服务降级后,指定调用的方法.(无论是服务调用超时还是异常,只要是服务不可用,都会进行降级调用指定方法)
    @HystrixCommand(fallbackMethod = "paymentInfo_timeoutHandler",commandProperties = {
            //设置该服务超时时间为3秒,如果服务执行超过3秒则进行降级
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_timeout(Integer id) {
        //int a = 1/0; // 让程序抛出异常
        try{TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}//模拟服务耗时5秒
        return "tomcat线程池:\t" + Thread.currentThread().getName() + "\t服务成功返回,id:\t" + id;
    }
    //该方法执行,用的是Hystrix的线程池
    public String paymentInfo_timeoutHandler(Integer id) {
        return "Hystrix线程池:\t" + Thread.currentThread().getName() + "\t服务超时或异常,id:\t" + id;
    }

    //----------------------------------- 服务熔断 -------------------------------------

    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties ={
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"), //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"), //请求次数.     默认20次
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), //时间窗口期,10秒.   默认10秒
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//失败率达到多少后跳闸.   默认50%
            /*     以上配置表示,统计在10秒内的10次请求,失败率达到60%,就进行服务熔断.
                   若在时间窗口期(10秒),请求次数不足请求阀值(10次),即使全都调用失败,断路器也不会打开,不会熔断服务.
                   若在时间窗口期内,请求总数超过了阀值20,比如请求了30次,有15次请求超时或异常,则错误率为15/20=75%,超过了失败率50%,就会熔断服务.
                   链路自动恢复调用:
                   当断路器打开后,原服务逻辑被切换成降级方法逻辑,hystrix会启动一个休眠时间窗(默认5秒),休眠期内降级方法逻辑会被调用.当休眠期过后,
                断路器会进入半开状态,让其中一个请求进行原服务逻辑调用,如果成功,则断路器关闭,切换回原服务逻辑,若失败,断路器继续开启,再进入一个休眠时间窗...
             */
    })
    @Override
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if (id < 0) {
            throw new RuntimeException("******id 不能为负数");
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+ "调用成功，流水号：" + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，o(╥﹏╥)o id：" + id;
    }
}
