package com.zto56.freight.common.core.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询基础入参
 *
 * @author zhangqingfu
 * @date 2022-06-30
 */
@ApiModel(description = "分页查询基础入参")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest extends Request {

    /**
     * 当前页，默认1
     */
    @ApiModelProperty("当前页，默认1")
    private long current = 1;

    /**
     * 每页显示条数，默认10
     */
    @ApiModelProperty("每页显示条数，默认10")
    private long size = 10;

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size < 1 ? 10 : size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current < 1 ? 1 : current;
    }
}
