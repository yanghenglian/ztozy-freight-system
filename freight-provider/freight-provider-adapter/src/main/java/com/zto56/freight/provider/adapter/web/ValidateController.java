package com.zto56.freight.provider.adapter.web;

import com.alibaba.fastjson.JSON;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.api.service.TestService;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.common.obj.Order;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 *
 * @author zhangqingfu
 * @date 2022-09-09
 */
@Api(tags = "参数校验")
@RestController
@RequestMapping("/validate")
@Validated
@Slf4j
public class ValidateController {


    @Autowired
    private TestService testService;


    @ApiOperation(value = "spring参数校验", notes = "RequestBody参数校验", httpMethod = "POST")
    @PostMapping("/test1")
    public BaseResponse<DogVO> test1(@RequestBody @Validated DogVO vo) {
        log.info("vo:{}", JSON.toJSONString(vo));
        return BaseResponse.ok(vo);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "int", name = "id", value = "id")
    })
    @ApiOperation(value = "spring参数校验", notes = "RequestParam参数校验", httpMethod = "POST")
    @PostMapping("/test2")
    public BaseResponse test2(@RequestParam @NotNull(message = "id不能为空") Integer id) {
        log.info("id:{}", id);
        return BaseResponse.ok();
    }


    @ApiOperation(value = "任意方法参数校验", notes = "RequestBody参数校验", httpMethod = "POST")
    @PostMapping("/test3")
    public BaseResponse<Order> test3(@RequestBody Order vo) {
        log.info("vo:{}", JSON.toJSONString(vo));
        testService.valid(vo);
        return BaseResponse.ok(vo);
    }

}
