package com.loanshark.gatewayserver.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

@Configuration
class RouteLocatorConfig {
    private static final String RESPONSE_TIME_HEADER = "X-Response-Time";
    private static final String PATH_REPLACEMENT = "/${segment}";

    @Bean
    public RouteLocator routeConfig(RouteLocatorBuilder routeBuilder) {
        return routeBuilder.routes()
                .route(p -> p
                        .path("/accounts/**")
                        .filters(f -> f.rewritePath("/accounts/(?<segment>.*)", PATH_REPLACEMENT)
                                .addResponseHeader(RESPONSE_TIME_HEADER, LocalDateTime.now().toString()))
                        .uri("lb://ACCOUNTS"))
                .route(p -> p
                        .path("/loans/**")
                        .filters(f -> f.rewritePath("/loans/(?<segment>.*)", PATH_REPLACEMENT)
                                .addResponseHeader(RESPONSE_TIME_HEADER, LocalDateTime.now().toString()))
                        .uri("lb://LOANS"))
                .route(p -> p
                        .path("/cards/**")
                        .filters(f -> f.rewritePath("/cards/(?<segment>.*)", PATH_REPLACEMENT)
                                .addResponseHeader(RESPONSE_TIME_HEADER, LocalDateTime.now().toString()))
                        .uri("lb://CARDS"))
                .build();
    }
}
