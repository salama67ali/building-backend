package com.suzasob.building_permission.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Building Permissions System API")
                        .description("API for managing building permits, inspections, and construction projects")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Building Permissions Team")
                                .email("support@buildingpermissions.com")
                                .url("https://buildingpermissions.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8050")
                                .description("Development Server"),
                        new Server()
                                .url("https://api.buildingpermissions.com")
                                .description("Production Server")));
    }
}
