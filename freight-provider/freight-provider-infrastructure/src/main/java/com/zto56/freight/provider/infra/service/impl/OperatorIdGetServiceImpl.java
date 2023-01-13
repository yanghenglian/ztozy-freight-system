package com.zto56.freight.provider.infra.service.impl;

import cn.monitor4all.logRecord.service.IOperatorIdGetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 操作日志操作人id获取
 * 若实现了接口后仍在注解手动传入OperatorID，则以传入的OperatorID优先。
 * @author zhangqingfu
 * @date 2022-09-08
 */
@Slf4j
@Component
public class OperatorIdGetServiceImpl implements IOperatorIdGetService {

    @Override
    public String getOperatorId() {
        // 查询操作人信息
        return "张三";
    }
}