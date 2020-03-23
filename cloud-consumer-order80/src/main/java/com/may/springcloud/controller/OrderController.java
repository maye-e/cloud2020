package com.may.springcloud.controller;

import com.may.springcloud.entities.CommonResult;
import com.may.springcloud.entities.Payment;
import com.may.springcloud.loadbalancer.MyLoadBalanceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RequestMapping("/order/consumer/")
@RestController
@Slf4j
public class OrderController {

//    private static final String PAYMENT_URL = "http://localhost:8001";//单机写死了地址
    private static final String PAYMENT_URL = "http://cloud-payment-service";//集群用服务名称调用

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private MyLoadBalanceImpl myLoadBalance;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL + "/payment/create",payment,CommonResult.class);
    }

    @GetMapping("payment/entityCreate")
    public CommonResult<Payment> entityCreate(Payment payment){
        return restTemplate.postForEntity(PAYMENT_URL + "/payment/create",payment,CommonResult.class).getBody();
    }

    @GetMapping("payment/getPaymentById/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL + "/payment/getPaymentById/"+id,CommonResult.class);
    }

    @GetMapping("payment/getForEntity/{id}")
    public CommonResult<Payment> getForEntity(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/getPaymentById/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()) {
            return entity.getBody();
        }else {
            return new CommonResult<>(444,"操作失败!");
        }
    }

    @GetMapping("payment/lb")
    public String getPaymentLB(){
        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        if (instances == null || instances.size() <= 0){
            return null;
        }
        //得到实际请求的服务
        ServiceInstance reqInstance = myLoadBalance.instances(instances);
        URI uri = reqInstance.getUri();
        return restTemplate.getForObject(uri + "payment/lb",String.class);
    }
}
