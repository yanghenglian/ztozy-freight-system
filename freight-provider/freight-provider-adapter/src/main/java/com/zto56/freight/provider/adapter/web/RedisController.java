package com.zto56.freight.provider.adapter.web;

import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.components.redis.util.RedisUtil;
import com.zto56.freight.components.redis.util.RedissonLock;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

/**
 *
 * @author zhangqingfu
 * @date 2022-07-27
 */
@Api(tags = "redis测试")
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedissonLock redissonLock;


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "key", value = "redis的key"),
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "value", value = "redis的value")
    })
    @ApiOperation(value = "设置值", notes = "往redis中设置key", httpMethod = "GET")
    @GetMapping("/set")
    public BaseResponse set(@RequestParam String key, @RequestParam String value) {
        redisUtil.set(key, value);
        return BaseResponse.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "key", value = "redis的key")
    })
    @ApiOperation(value = "获取值", notes = "在redis中查找key对应的value", httpMethod = "GET")
    @GetMapping("/get")
    public BaseResponse get(@RequestParam String key) {
        String value = redisUtil.get(key);
        return BaseResponse.ok("redis获取成功", value);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "key", value = "redis的key")
    })
    @ApiOperation(value = "删除值", notes = "删除redis中的key", httpMethod = "GET")
    @GetMapping("/delete")
    public BaseResponse delete(@RequestParam String key) {
        Boolean delete = redisUtil.delete(key);
        return Boolean.TRUE.equals(delete) ? BaseResponse.ok() : BaseResponse.failed();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "obj", value = "锁的对象")
    })
    @ApiOperation(value = "锁", notes = "锁", httpMethod = "GET")
    @GetMapping("/lock")
    public BaseResponse lock(@RequestParam String obj) {
        try {
            redissonLock.lock(obj, () -> {
                Thread.sleep(10000);
            });
        } catch (Exception e) {
            log.error("Exception msg:{} e:{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.ok();
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "string", name = "obj", value = "锁的对象")
    })
    @ApiOperation(value = "尝试锁", notes = "尝试锁", httpMethod = "GET")
    @GetMapping("/tryLock")
    public BaseResponse tryLock(@RequestParam String obj) {
        try {
            redissonLock.tryLock(obj, () -> {
                // 加锁后的业务代码
                Thread.sleep(10000);
            }, 2, 100);
        } catch (Exception e) {
            log.error("Exception msg:{} e:{}", e.getMessage(), Arrays.toString(e.getStackTrace()));
            return BaseResponse.failed(e.getMessage());
        }
        return BaseResponse.ok();
    }
}
