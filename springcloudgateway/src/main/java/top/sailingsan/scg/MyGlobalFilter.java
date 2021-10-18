/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.scg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;

public class MyGlobalFilter implements GlobalFilter, Ordered {
	final Logger log = LoggerFactory.getLogger(CustomGlobalFilter.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		log.info("-------- custom global filter");
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return -1;
	}
}