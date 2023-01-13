package com.zto56.freight.provider.adapter.web;

import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.consumer.client.api.dubbo.ByeService;
import com.zto56.freight.provider.infra.thread.ThreadExecutor;
import com.zto56.pay.client.api.TestServiceI;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-23
 */
@Api(tags = "测试")
@RestController
@RequestMapping("/test")
@RefreshScope
@Slf4j
@Validated
public class TestController {

    @Autowired
    @Qualifier(ThreadExecutor.demoExecutor)
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Value("${spring.application.name}")
    private String application;

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${test}")
    private String test;

    @DubboReference
    private ByeService byeService;

    @DubboReference(version = "1.0.0", group = "DEFAULT_GROUP")
    private TestServiceI testServiceI;

    @ApiOperation(value = "helloWorld", notes = "helloWorld", httpMethod = "GET")
    @GetMapping(value = "/helloworld")
    public BaseResponse helloWorld() {
        String str = "Hello, welcome to COLA world! " + application + "-" + active;
        return BaseResponse.ok("启动成功", str);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "int", name = "id", value = "id")
    })
    @ApiOperation(value = "根据id查找", notes = "根据id查找", httpMethod = "GET")
    @GetMapping("/findById/{id}")
    public BaseResponse findById(@PathVariable Integer id) {
        log.info("id:{}", id);
        return BaseResponse.ok(id);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "flag", value = "flag")
    })
    @ApiOperation(value = "测试抛出异常", notes = "测试抛出异常", httpMethod = "GET")
    @GetMapping("/exception")
    // @SentinelResource(value = "test")
    public BaseResponse test(Integer flag) {
        if (flag == null) {
            throw new IllegalArgumentException("参数异常");
        }
        return BaseResponse.ok(flag);
    }


    @ApiOperation(value = "读取nacos配置", notes = "读取nacos配置,自动刷新@RefreshScope", httpMethod = "GET")
    @GetMapping("/config")
    public BaseResponse config() {
        return BaseResponse.ok("配置读取成功", test);
    }


    @ApiOperation(value = "子线程traceId测试", notes = "子线程traceId测试方法", httpMethod = "GET")
    @GetMapping("/traceId")
    public BaseResponse traceId() {
        log.info("主线程traceId测试 thread:{}", Thread.currentThread());
        //主线程日志
        for (int i = 0; i < 5; i++) {
            //子线程日志
            threadPoolTaskExecutor.execute(() -> {
                log.info("子线程traceId测试 thread:{}", Thread.currentThread());
            });
        }
        return BaseResponse.ok();
    }


    @ApiOperation(value = "测试A调用B", notes = "测试A调用B", httpMethod = "GET")
    @GetMapping("/test/dubbo/in")
    public BaseResponse testDubboIn(@RequestParam String name) {
        log.info("测试A调用B");
        BaseResponse<String> result = byeService.bye(name);
        if (result.getSuccess()) {
            log.info("成功");
            return BaseResponse.ok("成功", result.getData());
        } else {
            log.error("失败");
            return BaseResponse.failed(result.getMsg());
        }
    }

    @ApiOperation(value = "测试dubbo调用pay", notes = "测试dubbo调用pay", httpMethod = "GET")
    @GetMapping("/test/dubbo/pay")
    public BaseResponse testDubboPay(@RequestParam String name) {
        log.info("测试dubbo调用pay");
        String say = testServiceI.sayHello(name);
        return BaseResponse.ok("成功", say);
    }
}
