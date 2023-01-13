package com.zto56.freight.provider.client.api.feign;


import com.zto56.freight.common.core.constant.ServiceConstant;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * feign测试
 *
 * @author zhangqingfu
 * @date 2022-09-09
 */
@FeignClient(value = ServiceConstant.SERVICE_ALIBABA_PROVIDER, fallbackFactory = TestApiFallbackFactory.class)
public interface TestApi {
    @GetMapping("/test/findById/{id}")
    BaseResponse findById(@PathVariable Integer id);

    @PostMapping("/dog/validation")
    BaseResponse<DogVO> validation1(@RequestBody DogVO vo);
}
