package com.zto56.freight.common.expand.valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;


/**
 * 通用 Validator
 *
 * @author zhangqingfu
 * @date 2022-09-09
 */
@Component
public class ValidateService {

    @Autowired
    private Validator validator;

    /**
     * 参数校验
     *
     * @param obj obj
     */
    public void validate(Object obj) {

        Set<ConstraintViolation<Object>> resultSet = validator.validate(obj);
        if (resultSet == null || resultSet.isEmpty()) {
            return;
        }
        resultSet.forEach(item -> {
            throw new IllegalArgumentException(item.getMessage());
        });
    }

}
