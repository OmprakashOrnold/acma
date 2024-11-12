package com.acma.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class AcmaIdentityGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run( AcmaIdentityGatewayApplication.class, args );
    }

}
