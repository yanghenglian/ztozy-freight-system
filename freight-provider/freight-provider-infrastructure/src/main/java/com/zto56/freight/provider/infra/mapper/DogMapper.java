package com.zto56.freight.provider.infra.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zto56.freight.provider.infra.database.UKA_FSC_DB;
import com.zto56.freight.provider.domain.entity.DogDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@UKA_FSC_DB
public interface DogMapper extends BaseMapper<DogDO> {
}