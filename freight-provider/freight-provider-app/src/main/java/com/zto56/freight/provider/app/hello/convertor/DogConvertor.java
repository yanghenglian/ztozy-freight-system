package com.zto56.freight.provider.app.hello.convertor;

import com.zto56.freight.provider.client.obj.dto.DogVO;
import com.zto56.freight.provider.domain.entity.DogDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface DogConvertor {

    @Mapping(target = "id", source = "dogDO.id")
    @Mapping(target = "name", source = "dogDO.name")
    @Mapping(target = "age", source = "dogDO.age")
    DogVO DO2VO(DogDO dogDO);

}
