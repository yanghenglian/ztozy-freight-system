package com.zto56.freight.provider.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 狗狗测试表
 */
@ApiModel(value = "狗狗测试表")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "test_dog")
public class DogDO implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "这是主键")
    private Long id;

    @TableField(value = "`name`")
    @ApiModelProperty(value = "这是名称")
    private String name;

    @TableField(value = "age")
    @ApiModelProperty(value = "这是年龄")
    private Integer age;

    private static final long serialVersionUID = 1L;
}