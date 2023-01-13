package com.zto56.freight.provider.infra.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zto56.freight.provider.infra.database.UKA_FSC_DB;
import com.zto56.freight.provider.domain.entity.DogDO;
import com.zto56.freight.provider.infra.mapper.DogMapper;
import com.zto56.freight.provider.infra.service.DogService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@UKA_FSC_DB
@CacheConfig(cacheNames = "dogDO")
public class DogServiceImpl extends ServiceImpl<DogMapper, DogDO> implements DogService {
    // 测试spring cache的读写

    @Cacheable(key = "#id")
    @Override
    public DogDO selectById(Long id) {
        return getById(id);
    }

    // CachePut会将方法的返回值放在缓存里
    @CachePut(key = "#dogDO.id")
    @Override
    public DogDO insert(DogDO dogDO) {
        boolean rs = save(dogDO);
        return rs ? dogDO : null;
    }


    @CachePut(key = "#dogDO.id")
    @Override
    public DogDO update(DogDO dogDO) {
        boolean rs = updateById(dogDO);
        return rs ? dogDO : null;
    }

    @CacheEvict(key = "#id")
    @Override
    public boolean deleteById(Long id) {
        return removeById(id);
    }
}
