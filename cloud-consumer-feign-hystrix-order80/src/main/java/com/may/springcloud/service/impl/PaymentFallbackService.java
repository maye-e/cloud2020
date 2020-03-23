package com.may.springcloud.service.impl;

import com.may.springcloud.service.PaymentHystrixService;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_ok(Integer id) {
        return "Fallback -- paymentInfo_ok";
    }

    @Override
    public String paymentInfo_timeout(Integer id) {
        return "Fallback -- paymentInfo_timeout";
    }
}
