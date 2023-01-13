package com.zto56.freight.consumer.adapter.web;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.api.feign.TestApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "测试feign")
@RestController
@RequestMapping("/feign")
@Slf4j
public class FeignTestController {

    @Autowired
    private TestApi testApi;

    @ApiOperation(value = "测试B调用A", notes = "测试B调用A", httpMethod = "GET")
    @GetMapping("/test")
    public BaseResponse test() {
        // 1.远程调用
        BaseResponse result = testApi.findById(RandomUtil.randomInt());
        if (result.getSuccess()) {
            log.info("成功");
        } else {
            log.error("失败");
        }
        log.info("result:{}", JSON.toJSONString(result));
        // 2.保存订单
        log.info("save order success");
        return BaseResponse.ok(result.getData());
    }


}
