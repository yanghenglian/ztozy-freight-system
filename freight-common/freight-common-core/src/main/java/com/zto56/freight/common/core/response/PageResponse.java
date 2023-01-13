package com.zto56.freight.common.core.response;


import com.zto56.freight.common.core.enums.ApiCodeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;

/**
 * 分页返回体
 * @author zhangqingfu
 * @date 2022-06-30
 */
@Data
public class PageResponse<T> extends Response {
    /**
     * 第几页
     */
    @ApiModelProperty("第几页")
    private long current = 1;


    /**
     * 每页显示条数，默认10
     */
    @ApiModelProperty("每页显示条数，默认10")
    private long size = 10;


    /**
     * 总条数
     */
    @ApiModelProperty("总条数")
    private long totalCount = 0;


    /**
     * 总页数
     */
    @ApiModelProperty("总页数")
    private long pages = 1;

    /**
     * 有无下一页
     */
    @ApiModelProperty("有无下一页")
    private Boolean hasNext;

    /**
     * 数据
     */
    @ApiModelProperty("数据")
    private Collection<T> data = Collections.emptyList();

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount < 0 ? 0 : totalCount;
    }

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

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = null == data ? Collections.emptyList() : data;
    }

    public long getPages() {
        return pages;
    }

    public void setPages(long pages) {
        this.pages = pages < 0 ? 0 : pages;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }


    /**
     * 成功
     *
     * @param data 数据
     * @param totalCount 总条数
     * @param pageIndex 当前页
     * @param pageSize 每页显示条数
     */
    public static <T> PageResponse<T> ok(Collection<T> data, long totalCount, long pageIndex, long pageSize) {
        long pages = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        return build(true, ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data, pageIndex, pageSize, totalCount, pages, pages > pageIndex);
    }

    /**
     * 成功
     *
     */
    public static <T> PageResponse<T> ok() {
        return build(true, ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), null, 0, 0, 0, 0, false);
    }


    /**
     * 失败
     *
     */
    public static <T> PageResponse<T> failed() {
        return build(false, ApiCodeEnum.FAILED.getCode(), ApiCodeEnum.FAILED.getMsg(), null, 0, 0, 0, 0, false);
    }

    /**
     * 失败
     *
     * @param errCode 错误码
     * @param errMessage 错误信息
     */
    public static <T> PageResponse<T> failed(Integer errCode, String errMessage) {
        return build(false, errCode, errMessage, null, 0, 0, 0, 0, false);
    }


    /**
     * 构建
     *
     * @param success 成功
     * @param code 编码
     * @param msg 信息
     * @param data 数据
     * @param current 当前
     * @param size 大小
     * @param totalCount 总计数
     * @param pages 总页数
     * @param hasNext 有无下一个
     */
    public static <T> PageResponse<T> build(Boolean success, Integer code, String msg, Collection<T> data, long current, long size, long totalCount, long pages, Boolean hasNext) {
        PageResponse<T> pageResponse = new PageResponse<>();
        pageResponse.setSuccess(success);
        pageResponse.setCode(code);
        pageResponse.setMsg(msg);
        pageResponse.setData(data);

        pageResponse.setCurrent(current);
        pageResponse.setSize(size);
        pageResponse.setTotalCount(totalCount);
        pageResponse.setPages(pages);
        pageResponse.setHasNext(hasNext);
        return pageResponse;
    }
}
