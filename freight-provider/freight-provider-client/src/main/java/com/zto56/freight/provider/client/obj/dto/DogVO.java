package com.zto56.freight.provider.client.obj.dto;

import cn.monitor4all.logRecord.annotation.LogRecordDiff;
import com.zto56.freight.common.core.request.Request;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**狗狗VO
 *
 * @author zhangqingfu
 * @date 2022-08-26
 */
@ApiModel(description = "DogVO描述")
@Data
public class DogVO extends Request {
    /**
     * ID
     */
    @ApiModelProperty("ID")
    @NotNull(message = "id不能为空")
    @LogRecordDiff(alias = "id")
    private Long id;
    /**
     * 名字
     */
    @ApiModelProperty("名字")
    @NotBlank(message = "name不能为空")
    @LogRecordDiff(alias = "狗名字")
    private String name;
    /**
     * 年龄
     */
    @ApiModelProperty("年龄")
    @LogRecordDiff(alias = "年龄")
    private Integer age;
    /**
     * 高
     */
    @ApiModelProperty("高")
    @LogRecordDiff(alias = "高")
    private Double high;
    /**
     * 价格
     */
    @ApiModelProperty("价格")
    @LogRecordDiff(alias = "价格")
    private BigDecimal price;
    /**
     * 是否卖出
     */
    @ApiModelProperty("是否卖出")
    @LogRecordDiff(alias = "卖出")
    private Boolean sell;
    /**
     * 出生
     */
    @ApiModelProperty("出生")
    @LogRecordDiff(alias = "出生")
    private Date birth;
}
