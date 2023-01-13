package com.zto56.freight.provider.infra.database;

import com.baomidou.dynamic.datasource.annotation.DS;

import java.lang.annotation.*;

/**
 * uka ntocc db
 *
 * @author yanghenglian
 * @date 2022/07/14
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@DS("uka_ntocc")
public @interface UKA_NTOCC_DB {
}


