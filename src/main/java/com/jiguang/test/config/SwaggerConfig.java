package com.jiguang.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @Description Swagger配置
 * @Date 2019/8/6 9:01 AM
 * @Version 1.0
 **/
@Configuration
public class SwaggerConfig {

    //@Value("${swagger.enable}")
    private boolean enableSwagger = true;

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).enable(enableSwagger)
                .apiInfo(apiInfo())
                .select()
                //只暴露@RestController注解修饰的controller,隐藏了系统自带的@controller注解修饰的controller
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("THC Market说明文档")
                .description("THC Market接口文档")
                .version("3.7.1")
                .build();
    }
}
