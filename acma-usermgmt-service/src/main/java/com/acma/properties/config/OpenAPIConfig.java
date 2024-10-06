package com.acma.properties.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "ACMA User Management Service",
                               version = "1.0",
                               description = "ACMA User Management Service",
                               license = @License(name ="Techhubvault",url = "https://techhubvault.com",identifier = "Copyright @2024"),
                               contact = @Contact(email = "techhubvault@gmail.com")))
@SecurityScheme( name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class OpenAPIConfig {


}
