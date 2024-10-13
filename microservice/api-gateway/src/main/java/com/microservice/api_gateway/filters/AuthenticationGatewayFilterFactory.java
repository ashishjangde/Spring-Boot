package com.microservice.api_gateway.filters;

import com.microservice.api_gateway.services.JwtService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpStatus;

@Component
@Slf4j
public class AuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthenticationGatewayFilterFactory.Config> {

    private final JwtService jwtService;

    @Data
    public static class Config {
        private boolean enabled;
    }



    @Override
    public GatewayFilter apply(Config config) {
        return  ((exchange, chain) -> {

            log.info("Is Enabled {}", config.enabled);

            if (! config.enabled) return chain.filter(exchange);

            String Authentication = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (Authentication == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String token = Authentication.split("Bearer ")[1];
            Long userId = jwtService.getUserIdFromToken(token);

            exchange.getRequest()
                    .mutate()
                    .header("X-USER-ID", userId.toString())
                    .build();

             return chain.filter(exchange);
        });
    }

    public AuthenticationGatewayFilterFactory(JwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }


}
