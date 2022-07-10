/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.scg;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MyGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("-------- custom global filter");
        chain.filter(exchange);
        return chain.filter(exchange)
                .doOnSuccess(aVoid -> System.out.println("sucess:" + exchange))
                .doOnError(throwable -> System.out.println("error: " + exchange));
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
