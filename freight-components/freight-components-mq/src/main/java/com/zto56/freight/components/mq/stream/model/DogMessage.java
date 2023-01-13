package com.zto56.freight.components.mq.stream.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */

@Data
public class DogMessage extends AbstractStreamMQ {
    public static final String TOPIC = "test-topic1";
    public static final String OUTPUT = "demo01-OUTPUT";
    public static final String INPUT = "demo01-INPUT";

    private Long id;
    private String name;
    private Integer age;
    private Double high;
    private BigDecimal price;
    private Boolean sell;
    private Date birth;

}
