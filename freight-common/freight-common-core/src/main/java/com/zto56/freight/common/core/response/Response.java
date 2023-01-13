package com.zto56.freight.common.core.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Response基类
 * @author zhangqingfu
 * @date 2022-06-30
 */
@ApiModel(description = "Response基类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 执行是否成功
     */
    @ApiModelProperty("执行是否成功")
    private Boolean success;
    /**
     * 业务状态码
     */
    @ApiModelProperty("业务状态码")
    private Integer code;
    /**
     * 业务描述
     */
    @ApiModelProperty("业务描述")
    private String msg;
    /**
     * traceId
     */
    @ApiModelProperty("traceId")
    private String traceId;
    /**
     * 时间戳
     */
    @ApiModelProperty("时间戳")
    private Long timestamp;
}
