package com.zto56.freight.provider.common.obj;

import cn.monitor4all.logRecord.annotation.LogRecordDiff;
import com.github.fashionbrot.validated.annotation.NotBlank;
import com.zto56.freight.common.core.request.Request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * 测试
 * @author zhangqingfu
 * @date 2022-09-08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Request {
    /**
     * 订单ID
     */
    @LogRecordDiff(alias = "订单ID")
    @NotBlank(msg = "订单ID不能为空")
    private Long orderId;

    /**
     * 订单号
     */
    @LogRecordDiff(alias = "订单号")
    @NotBlank(msg = "订单号不能为空")
    private String orderNo;

    /**
     * 产品名称
     */
    @LogRecordDiff(alias = "产品名称")
    private String productName;

    /**
     * 采购人
     */
    @LogRecordDiff(alias = "采购人")
    private String purchaseName;

    /**
     * 创建时间
     */
    @LogRecordDiff(alias = "创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @LogRecordDiff(alias = "创建人")
    private UserDO creator;

    /**
     * 列表项
     */
    @LogRecordDiff(alias = "列表项")
    private List<String> items;

    @Data
    public static class UserDO extends Request {
        /**
         * 用户ID
         */
        @LogRecordDiff(alias = "用户ID")
        private Long userId;
        /**
         * 用户姓名
         */
        @LogRecordDiff(alias = "用户姓名")
        private String userName;
    }
}
