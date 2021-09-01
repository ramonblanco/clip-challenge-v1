package com.example.clip.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2).select()
//                .apis(RequestHandlerSelectors.any())
                .apis(RequestHandlerSelectors.basePackage("com.example.clip.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(getApiInfo());
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo("Clip Chalenge API", "Clip Challenge API", "1.0", "http://www.localhost:8080/",
                new Contact("Ramon Blanco", "https://www.linkedin.com/in/jos%C3%A9-ram%C3%B3n-blanco-mart%C3%ADnez-b431b4152/", "ramonblanco117@gmail.com"),
                "For testing and development purposes", "LICENSE URL", Collections.emptyList());
    }

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }

}
