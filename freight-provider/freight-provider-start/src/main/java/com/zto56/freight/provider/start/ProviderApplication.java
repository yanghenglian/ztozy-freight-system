package com.zto56.freight.provider.start;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Import(cn.hutool.extra.spring.SpringUtil.class)
@SpringBootApplication(scanBasePackages = {"com.zto56.freight"})
// 扫描@FeignClient注解
@EnableFeignClients(basePackages = {"com.zto56.freight"})
// 扫描@DubboService注解
@EnableDubbo(scanBasePackages = "com.zto56.freight")
// 扫描mapper
@MapperScan("com.zto56.freight.provider.infra.mapper")
@EnableDiscoveryClient
// stream MQ
// @EnableBinding({DogSource.class, DogSink.class})
// 异步处理
@EnableAsync
// 定时任务
@EnableScheduling
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }


    // @Before("execution(* org.springframework.cloud.client.serviceregistry.ServiceRegistry.register(*)) && args(registration)")
    // public void beforeRegister(Registration registration) {
    //     DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
    //     dubboBootstrap.start();
    // }
}
