package com.zto56.freight.provider.infra.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * 线程池配置
 *
 * @author zhangqingfu
 * @date 2022-07-04
 */
@Configuration
public class ThreadExecutor {

    public static final String demoExecutor = "demoExecutor";

    @Bean(demoExecutor)
    public ThreadPoolTaskExecutor demoExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20); // 线程池维护线程的最少数量
        executor.setMaxPoolSize(300);  // 线程池维护线程的最大数量
        executor.setQueueCapacity(10); // 缓存队列
        executor.setThreadNamePrefix(demoExecutor + "-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
        executor.setKeepAliveSeconds(60); // 允许的空闲时间
        executor.initialize();
        return executor;
    }

}
