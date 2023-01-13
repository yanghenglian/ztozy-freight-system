package com.zto56.freight.consumer.adapter.web;

import com.zto56.freight.common.core.request.PageRequest;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.common.core.response.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试")
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Value("${spring.application.name}")
    private String application;

    @Value("${spring.profiles.active}")
    private String active;


    @ApiOperation(value = "测试", notes = "测试", httpMethod = "POST")
    @PostMapping("/test1")
    public PageResponse test1(@RequestBody PageRequest request) {
        return PageResponse.ok();
    }

    @GetMapping(value = "/helloworld")
    public BaseResponse helloWorld() {
        String str = "Hello, welcome to COLA world! " + application + "-" + active;
        return BaseResponse.ok("启动成功", str);
    }

}
