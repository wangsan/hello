/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.scg;

import reactor.core.publisher.Mono;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {

	public MyGatewayFilterFactory() {
		super(MyGatewayFilterFactory.Config.class);
	}

	@Override
	public GatewayFilter apply(MyGatewayFilterFactory.Config config) {
		// grab configuration from Config object
		return (exchange, chain) -> {
			ServerHttpRequest.Builder builder = exchange.getRequest().mutate();

			System.out.println(String.format("before ------- \n %s", exchange.getRequest()));
			long start = System.currentTimeMillis();
			return chain.filter(exchange.mutate().request(builder.build()).build())
					.then(Mono.fromRunnable(() -> {
						long elapse = System.currentTimeMillis() - start;
						ServerHttpResponse response = exchange.getResponse();
						System.out.println(String.format("after -------  elapse %s ms \n %s", elapse, response));
					}));
		};
	}

	public static class Config {
		//Put the configuration properties for your filter here
	}

}