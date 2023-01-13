package com.zto56.freight.provider.start.config;

import com.zto56.freight.common.core.enums.ApiCodeEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-27
 */
// @Profile(value = "local")
@Configuration
@EnableSwagger2
// @EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    @Order(value = 1)
    public Docket groupRestApi() {
        // 添加全局响应状态码
        List<Response> responseMessageList = new ArrayList<>();
        // 获取自定义响应码
        Arrays.stream(ApiCodeEnum.values()).forEach(
                errorEnums -> responseMessageList.add(new ResponseBuilder()
                        .code(errorEnums.getCode().toString())
                        .description(errorEnums.getMsg())
                        .build()));


        return new Docket(DocumentationType.SWAGGER_2)
                // 添加全局响应状态码，可根据不同系统定义不同的响应码信息
                .globalResponses(HttpMethod.GET, responseMessageList)
                .globalResponses(HttpMethod.PUT, responseMessageList)
                .globalResponses(HttpMethod.POST, responseMessageList)
                .globalResponses(HttpMethod.DELETE, responseMessageList)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo groupApiInfo() {
        return new ApiInfoBuilder()
                .title(applicationName + "接口文档")
                .description(applicationName + "接口文档")
                .termsOfServiceUrl("http://127.0.0.1:7001")
                .contact(new Contact("中通智运", "", ""))
                .version("1.0")
                .build();
    }


}
