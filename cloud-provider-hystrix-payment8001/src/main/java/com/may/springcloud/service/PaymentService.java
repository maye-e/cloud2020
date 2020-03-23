package com.may.springcloud.service;

public interface PaymentService {

    String paymentInfo_ok(Integer id);
    String paymentInfo_timeout(Integer id);
    String paymentCircuitBreaker(Integer id);
}
