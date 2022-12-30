package com.thiencode.springbootsecurityhibernatejwt.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableConfigurationProperties
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "Authorization",
                type = SecuritySchemeType.HTTP,
                bearerFormat = "Bearer [token]",
                scheme = "bearer",
                in = SecuritySchemeIn.HEADER,
                description = "Access token"
        )})
@OpenAPIDefinition(
        info = @Info(title = "jwt Open Api", version = "1.0.0", description = "Documentation ShareCv Marketplace v1.0.0"),
        security = @SecurityRequirement(name = "Authorization")
)
public class OpenApiConfig {
}
