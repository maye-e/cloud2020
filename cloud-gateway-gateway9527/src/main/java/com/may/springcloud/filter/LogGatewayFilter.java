package com.may.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Component
@Slf4j
public class LogGatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("*****进入 LogGatewayFilter" + new Date());
        // localhost:9627/payment/lb?uname=xxx 才可以访问
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (uname == null){
            log.error("用户名为空,非法访问!!!");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);//放行,或进入到下一个过滤器
    }

    @Override
    public int getOrder() {
        return 0;//过滤器的顺序,数字越小,优先级越高
    }
}
