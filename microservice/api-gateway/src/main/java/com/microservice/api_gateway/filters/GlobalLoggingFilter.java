package com.microservice.api_gateway.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class GlobalLoggingFilter implements GlobalFilter , Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {  //mono void means promise nonBlocking async behaviour like js

        log.info("called Url {}", exchange.getRequest().getURI()); //preFilter

        return chain.filter(exchange).then(Mono.fromRunnable(()->{  //postFilter
            log.info("Request Method: {}", exchange.getRequest().getMethod());
        }));
    }

    @Override
    public int getOrder() {   //from here we can change the filter order
        return 5;
    }
}
