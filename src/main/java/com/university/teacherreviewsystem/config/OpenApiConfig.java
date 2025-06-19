/**
 * Назначение: Настраивает Swagger UI для документации API
 */

package com.university.teacherreviewsystem.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Teacher Review System API")
                        .version("1.0.0")
                        .description("API documentation for the Teacher Review System project"));
    }
}
