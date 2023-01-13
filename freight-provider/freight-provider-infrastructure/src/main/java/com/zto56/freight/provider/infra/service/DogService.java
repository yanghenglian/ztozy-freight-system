package com.zto56.freight.provider.infra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zto56.freight.provider.domain.entity.DogDO;
public interface DogService extends IService<DogDO>{

    /**
     * 根据id查找,优先走缓存
     *
     * @param id ID
     */
    DogDO selectById(Long id);

    /**
     * 新增,数据缓存
     *
     * @param dogDO 狗狗
     * @return {@link DogDO}
     */
    DogDO insert(DogDO dogDO);

    /**
     * 根据id修改,数据缓存
     *
     * @param dogDO 狗狗
     * @return {@link DogDO}
     */
    DogDO update(DogDO dogDO);

    /**
     * 根据id删除DB和缓存
     *
     * @param id ID
     * @return boolean
     */
    boolean deleteById(Long id);
}
