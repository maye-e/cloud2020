package com.may.springcloud.loadbalancer;

import cn.hutool.core.lang.Console;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MyLoadBalanceImpl implements LoadBalancer {

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    public final int getAndIncrement(){
        int current;
        int next;
        do {
            current = this.atomicInteger.get();
            next = current >= 2147483647 ? 0 : current + 1;// Integer.MAX_VALUE = 2147483647
        } while (!this.atomicInteger.compareAndSet(current,next));//期望值 , 更新值
        Console.log("*****第 {} 次访问",next);
        return next;
    }

    @Override
    public ServiceInstance instances(List<ServiceInstance> serviceInstances) {
        // 第 x 次访问除以总服务数取余,作为请求服务的下标
        int index = getAndIncrement() % serviceInstances.size();
        return serviceInstances.get(index);
    }
}
