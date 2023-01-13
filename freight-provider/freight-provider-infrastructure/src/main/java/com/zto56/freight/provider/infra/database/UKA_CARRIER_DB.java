package com.zto56.freight.provider.infra.database;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * @author yanghenglian
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("uka_carrier")
public @interface UKA_CARRIER_DB {
}


