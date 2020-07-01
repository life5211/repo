package com.xhu.bill.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author user17
 * @version 1.0
 * @date 2019-11-25 16:39
 */
@Configuration
@EnableSwagger2
@Profile({"dev", "test"})
public class SwaggerConfig {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Bill API接口文档")
                .description("多用户账单管理")
                .version("1.0.0")
                .build();
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.xhu.bill.web.controller"))
                //这里写的是API接口所在的包位置
                .paths(PathSelectors.any())
                .build();
    }
}
