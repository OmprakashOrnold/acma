package com.acma.properties.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RouteConfig {


    @Bean
    public RouteLocator routeDefinitions(RouteLocatorBuilder builder){
        log.info("Initializing route definitions...");

        return builder.routes()
                .route( "01-usermgmt-getAllUsers-routes",
                        (route ->route.path( "/users" ).uri( "lb://ACMA-USERMGMT-SERVICE")))
                .build();
    }


}
