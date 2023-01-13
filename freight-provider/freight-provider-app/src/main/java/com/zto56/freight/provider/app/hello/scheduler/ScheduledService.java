package com.zto56.freight.provider.app.hello.scheduler;


import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 * @author zhangqingfu
 * @date 2022-09-07
 */
@Component
@Slf4j
public class ScheduledService {

    // @Scheduled(cron = "0/5 * * * * *")
    public void scheduled() {
        log.info("我是一个定时任务  time:{}", DateUtil.date());
    }

}


