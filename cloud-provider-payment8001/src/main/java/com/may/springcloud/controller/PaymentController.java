package com.may.springcloud.controller;

import com.may.springcloud.entities.CommonResult;
import com.may.springcloud.entities.Payment;
import com.may.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/payment/")
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入结果:" + result);
        if (result > 0){
            return new CommonResult(200,"插入成功,serverPort:" + serverPort,result);
        }else {
            return new CommonResult(444,"插入失败");
        }
    }

    @GetMapping("getPaymentById/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        if (payment != null){
            return new CommonResult(200,"查询成功,serverPort:" + serverPort,payment);
        }else {
            return new CommonResult(444,"没有记录");
        }
    }

    @GetMapping("discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.forEach(System.out::println);
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        instances.forEach((instance) -> {
            log.info(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());
        });

        return this.discoveryClient;
    }

    @GetMapping(value = "lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("timeout")
    public String timeout(){
        try{TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
        return serverPort;
    }

}
