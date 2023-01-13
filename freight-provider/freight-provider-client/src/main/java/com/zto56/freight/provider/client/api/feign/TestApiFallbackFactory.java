package com.zto56.freight.provider.client.api.feign;

import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.provider.client.obj.dto.DogVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-24
 */
@Slf4j
@Component
public class TestApiFallbackFactory implements FallbackFactory<TestApi> {

    @Override
    public TestApi create(Throwable cause) {
        return new TestApi() {
            @Override
            public BaseResponse findById(Integer id) {
                log.error("feign请求异常 msg:{} e:{}", cause.getMessage(), Arrays.toString(cause.getStackTrace()));
                return BaseResponse.sysFailed(cause.getMessage());
            }

            @Override
            public BaseResponse<DogVO> validation1(DogVO vo) {
                log.error("feign请求异常 msg:{} e:{}", cause.getMessage(), Arrays.toString(cause.getStackTrace()));
                return BaseResponse.sysFailed(cause.getMessage());
            }
        };
    }
}
