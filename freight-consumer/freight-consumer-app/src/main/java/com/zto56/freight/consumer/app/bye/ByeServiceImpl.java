package com.zto56.freight.consumer.app.bye;

import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.consumer.client.api.dubbo.ByeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-30
 */
@Slf4j
@DubboService
public class ByeServiceImpl implements ByeService {
    @Override
    public BaseResponse<String> bye(String name) {
        String data = "bye " + name;
        log.info("bye:{}", data);
        return BaseResponse.ok("成功", data);
    }
}
