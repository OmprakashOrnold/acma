package com.acma.properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AcmaRegistryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run( AcmaRegistryServiceApplication.class, args );
    }

}
