/*
 * Copyright (C) 2021 Baidu, Inc. All Rights Reserved.
 */
package top.sailingsan.scg;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
public class MyGatewayFilterFactory extends AbstractGatewayFilterFactory<MyGatewayFilterFactory.Config> {

    public MyGatewayFilterFactory() {
        super(MyGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(MyGatewayFilterFactory.Config config) {
        // grab configuration from Config object
        return (exchange, chain) -> {
            ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
            exchange.getRequest().getBody().map(db -> db.capacity()).subscribe(reqSize -> {
                log.info("before ------- reqSize is {}", reqSize);
            });

            long start = System.currentTimeMillis();
            return chain.filter(exchange)
                    .then(Mono.fromRunnable(() -> {
                        long elapse = System.currentTimeMillis() - start;
                        ServerHttpResponse response = exchange.getResponse();
                        log.info("after -------  elapse {} ms, respStatus is {} ", elapse, response.getRawStatusCode());
                    }));
        };
    }

    public static class Config {
        //Put the configuration properties for your filter here
    }

}
