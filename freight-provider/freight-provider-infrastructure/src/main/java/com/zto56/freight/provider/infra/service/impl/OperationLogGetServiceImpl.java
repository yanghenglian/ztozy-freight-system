package com.zto56.freight.provider.infra.service.impl;

import cn.monitor4all.logRecord.bean.LogDTO;
import cn.monitor4all.logRecord.service.IOperationLogGetService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 操作日志处理
 * @author zhangqingfu
 * @date 2022-09-08
 */
@Slf4j
@Component
public class OperationLogGetServiceImpl implements IOperationLogGetService {
    @Override
    public void createLog(LogDTO logDTO) {
        log.info("logDTO:{}", JSON.toJSONString(logDTO, SerializerFeature.WriteMapNullValue));
    }
}
