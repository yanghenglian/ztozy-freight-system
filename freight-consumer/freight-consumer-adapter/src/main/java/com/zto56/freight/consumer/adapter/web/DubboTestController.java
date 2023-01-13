package com.zto56.freight.consumer.adapter.web;

import com.alibaba.fastjson.JSON;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.api.dubbo.HelloService;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.*;

@Api(tags = "测试Dubbo")
@RestController
@RequestMapping("/dubbo")
@Slf4j
public class DubboTestController {
    @DubboReference
    private HelloService helloService;

    @ApiOperation(value = "测试B调用A", notes = "测试consumer调用provider", httpMethod = "GET")
    @GetMapping("/test1")
    public BaseResponse test1(@RequestParam String name) {
        log.info("测试B调用A");
        BaseResponse<String> result = helloService.hello(name);
        if (result.getSuccess()) {
            log.info("成功,result:{}", JSON.toJSONString(result));
            return BaseResponse.ok("成功", result.getData());
        } else {
            log.error("失败,result:{}", JSON.toJSONString(result));
            return BaseResponse.failed(result.getMsg());
        }
    }

    @ApiOperation(value = "测试B调用A调pay", notes = "测试consumer调用provider调用pay", httpMethod = "GET")
    @GetMapping("/test2")
    public BaseResponse test2(@RequestParam String name) {
        log.info("测试B调用A调pay");
        BaseResponse<String> result = helloService.test(name);
        if (result.getSuccess()) {
            log.info("成功");
            return BaseResponse.ok("成功", result.getData());
        } else {
            log.error("失败,result:{}", JSON.toJSONString(result));
            return BaseResponse.failed(result.getMsg());
        }
    }


    @ApiOperation(value = "测试B调用A hello2", notes = "测试B调用A hello2", httpMethod = "GET")
    @GetMapping("/test3")
    public BaseResponse test3(@RequestParam String name) {
        log.info("测试B调用A hello2");
        String s = helloService.hello2(name);
        return BaseResponse.ok("成功", s);

    }


    @ApiOperation(value = "测试B调用A helloOrder", notes = "测试B调用A helloOrder", httpMethod = "POST")
    @PostMapping("/test4")
    public BaseResponse test4(@RequestBody Order order) {
        log.info("测试B调用A helloOrder");
        BaseResponse<Order> result = helloService.helloOrder(order);
        if (result.getSuccess()) {
            log.info("成功,result:{}", JSON.toJSONString(result));
            return BaseResponse.ok("成功", result.getData());
        } else {
            log.error("失败,result:{}", JSON.toJSONString(result));
            return BaseResponse.failed(result.getMsg());
        }
    }

    @ApiOperation(value = "测试B调用A helloDog", notes = "测试B调用A helloDog", httpMethod = "POST")
    @PostMapping("/test5")
    public BaseResponse<DogVO> test5(@RequestBody DogVO dogVO) {
        log.info("测试B调用A helloDog");
        BaseResponse<DogVO> result = helloService.helloDog(dogVO);
        if (result.getSuccess()) {
            log.info("成功,result:{}", JSON.toJSONString(result));
            return BaseResponse.ok("成功", result.getData());
        } else {
            log.error("失败,result:{}", JSON.toJSONString(result));
            return BaseResponse.failed(result.getMsg());
        }
    }
}
