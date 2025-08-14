package dev.evaldo.perfectnumber.config;

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
                        .title("Perfect Number API")
                        .description("API para detectar números perfeitos (Clean Architecture)")
                        .version("v1"));
    }
}
