package com.zto56.freight.components.redis.util;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


/**
 * Redisson锁
 * 使用方式:Autowired
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
@Component
@Slf4j
public class RedissonLock {
    private final static String LOCKER_PREFIX = "lock:";

    @Autowired
    private RedissonClient redissonClient;


    public <T> void lock(String object, AquiredLockWorker<T> worker) throws Exception {
        String key = LOCKER_PREFIX + object;
        RLock lock = redissonClient.getLock(key);
        try {
            lock.lock();
            worker.invokeAfterAquiredLock();
        } catch (Exception e) {
            log.error("key:[{}]加锁期间异常 msg:{} e:{}", key, e.getMessage(), Arrays.toString(e.getStackTrace()));
            throw e;
        } finally {
            lock.unlock();
            log.info("key:[{}]解锁成功", key);
        }
    }


    /**
     * 尝试加锁
     *
     * @param object 锁的对象
     * @param worker 加锁成功后的业务操作
     * @param lockTime 锁定时间
     * @throws Exception 异常
     */
    public <T> void tryLock(String object, AquiredLockWorker<T> worker, long waitTime, long lockTime) throws Exception {
        String key = LOCKER_PREFIX + object;
        RLock lock = redissonClient.getLock(key);
        boolean success = lock.tryLock(waitTime, lockTime, TimeUnit.SECONDS);
        if (success) {
            try {
                log.info("key:[{}]加锁成功", key);
                worker.invokeAfterAquiredLock();
            } catch (Exception e) {
                log.error("key:[{}]加锁期间异常 msg:{} e:{}", key, e.getMessage(), Arrays.toString(e.getStackTrace()));
                throw e;
            } finally {
                lock.unlock();
                log.info("key:[{}]解锁成功", key);
            }
        } else {
            log.error("key:[{}]加锁失败", key);
            throw new RuntimeException("加锁失败");
        }
    }
}
