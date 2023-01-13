package com.zto56.freight.components.redis.util;

import cn.hutool.core.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * redis 工具类
 * 使用方式:Autowired
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisUtil {
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键
     * @param value 缓存的值
     */
    public <T> void set(String key, T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key 缓存的键
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间单位
     */
    public <T> void set(String key, T value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     *
     * @param key 缓存的键
     * @param timeout 超时时间,单位秒
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key 缓存的键
     * @param timeout 超时时间
     * @param timeUnit 时间单位
     * @return true=设置成功；false=设置失败
     */
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        Assert.isTrue(timeout > 0, "时间不能小于0");
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 缓存的键 不能为 null
     * @return 时间(秒) 返回 0代表为永久有效
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }


    /**
     * 判断 key 是否存在
     *
     * @param key 缓存的键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存的键
     * @return 缓存键对应的数据
     */
    public <T> T get(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     *
     * @param key 缓存的键
     * @return true=成功；false=失败
     */
    public Boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     *
     * @param collection 多个对象
     */
    public Long delete(Collection collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> long setList(String key, List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键
     * @return 缓存的数据
     */
    public <T> List<T> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存的键
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        for (T t : dataSet) {
            setOperation.add(t);
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key 缓存的键
     */
    public <T> Set<T> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 缓存Map
     *
     * @param key 缓存的键
     * @param dataMap map
     */
    public <T> void setMap(String key, Map<String, T> dataMap) {
        if (dataMap != null) {
            redisTemplate.opsForHash().putAll(key, dataMap);
        }
    }

    /**
     * 获得缓存的Map
     *
     * @param key 缓存的键
     */
    public <T> Map<String, T> getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 往Hash中存入数据
     *
     * @param key 缓存的键
     * @param hKey Hash键
     * @param value 值
     */
    public <T> void setHash(String key, String hKey, T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    /**
     * 获取Hash中的数据
     *
     * @param key 缓存的键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    public <T> T getHash(String key, String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    /**
     * 获取多个Hash中的数据
     *
     * @param key 缓存的键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    public <T> List<T> getHash(String key, Collection hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 递增
     *
     * @param key   缓存的键
     * @param delta 要增加几(大于0)
     * @return 递增后的值
     */
    public Long incr(String key, Long delta) {
        Assert.isTrue(delta > 0, "递增因子必须大于0");
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     *
     * @param key   缓存的键
     * @param delta 要减少几
     * @return 递减后的值
     */
    public Long decr(String key, Long delta) {
        Assert.isTrue(delta > 0, "递减因子必须大于0");
        return redisTemplate.opsForValue().increment(key, -delta);
    }

}
