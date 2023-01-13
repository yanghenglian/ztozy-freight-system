package com.zto56.freight.components.redis.util;


/**
 * 获取锁后需要处理的逻辑
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
public interface AquiredLockWorker<T> {
    /**
     * 获取锁后调用
     */
    void invokeAfterAquiredLock() throws Exception;
}