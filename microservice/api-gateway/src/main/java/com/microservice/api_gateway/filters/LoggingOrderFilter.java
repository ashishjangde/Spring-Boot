package com.microservice.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingOrderFilter extends AbstractGatewayFilterFactory<LoggingOrderFilter.Config> {

    public static class Config {

    }
    @Override
    public GatewayFilter apply(Config config) {

        return ((exchange, chain) -> {

            log.info("Order Filter Pre {}", exchange.getRequest().getURI());

            return chain.filter(exchange);

        });


//        return new GatewayFilter() {
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//                return null;
//            }
//
//        };
    }

    public LoggingOrderFilter() {
        super(Config.class);
    }
}
