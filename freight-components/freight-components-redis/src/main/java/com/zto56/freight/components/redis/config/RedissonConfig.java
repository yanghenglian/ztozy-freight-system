package com.zto56.freight.components.redis.config;

import cn.hutool.core.util.StrUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Redisson配置
 * @author zhangqingfu
 * @date 2022-07-27
 */
@Configuration
@ConditionalOnProperty(name = "spring.redis.cluster.nodes")
public class RedissonConfig {
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        List<String> nodes = redisProperties.getCluster().getNodes();
        String[] addresses = new String[nodes.size()];
        for (int i = 0; i < nodes.size(); i++) {
            List<String> split = StrUtil.split(nodes.get(i), ":");
            addresses[i] = StrUtil.format("redis://{}:{}", split.get(0), split.get(1));
        }
        Config config = new Config();
        config.useClusterServers()
                .setScanInterval(5000)
                .setPassword(redisProperties.getPassword())
                .addNodeAddress(addresses);
        return Redisson.create(config);
    }

}
