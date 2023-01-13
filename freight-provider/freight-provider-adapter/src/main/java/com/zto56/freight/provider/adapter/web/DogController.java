package com.zto56.freight.provider.adapter.web;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zto56.freight.common.core.response.BaseResponse;
import com.zto56.freight.common.core.response.PageResponse;
import com.zto56.freight.provider.domain.entity.DogDO;
import com.zto56.freight.provider.infra.service.DogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 修狗测试controller
 *
 * @author zhangqingfu
 * @date 2022-08-29 17:06:45
 */
@Api(tags = "修狗测试controller")
@RestController
@RequestMapping("/dog")
@RefreshScope
@Validated
@Slf4j
public class DogController {

    @Autowired
    private DogService dogService;




    @ApiOperation("分页查询所有数据")
    @GetMapping
    public PageResponse<DogDO> selectAll(Page<DogDO> page, DogDO dogDO) {
        Page<DogDO> data = dogService.page(page, new QueryWrapper<>(dogDO));
        return PageResponse.ok(data.getRecords(), data.getTotal(), data.getCurrent(), data.getSize());
    }


    @ApiOperation("通过主键查询单条数据")
    @GetMapping("/{id}")
    public BaseResponse<DogDO> selectOne(@PathVariable Long id) {
        return BaseResponse.ok(dogService.getById(id));
    }

    @ApiOperation("新增数据")
    @PostMapping
    public BaseResponse<Long> insert(@RequestBody DogDO dogDO) {
        boolean rs = dogService.save(dogDO);
        return rs ? BaseResponse.ok(dogDO.getId()) : BaseResponse.failed();
    }


    @ApiOperation("修改数据")
    @PutMapping
    public BaseResponse<?> update(@RequestBody DogDO dogDO) {
        boolean rs = dogService.updateById(dogDO);
        return rs ? BaseResponse.ok() : BaseResponse.failed();
    }


    @ApiOperation("单条/批量删除数据")
    @DeleteMapping
    public BaseResponse<?> delete(@RequestParam("idList") List<Long> idList) {
        boolean rs = dogService.removeByIds(idList);
        return rs ? BaseResponse.ok() : BaseResponse.failed();
    }


    /**
     * 通过主键查询单条数据(缓存)
     */
    @ApiOperation("通过主键查询单条数据(缓存)")
    @GetMapping("/cache/{id}")
    public BaseResponse<DogDO> selectById(@PathVariable Long id) {
        return BaseResponse.ok(dogService.selectById(id));
    }

    /**
     * 新增数据(缓存)
     */
    @ApiOperation("新增数据(缓存)")
    @PostMapping("/cache")
    public BaseResponse<DogDO> cacheSave(@RequestBody DogDO dogDO) {
        dogService.insert(dogDO);
        return BaseResponse.ok();
    }

    /**
     * 修改数据(缓存)
     */
    @ApiOperation("修改数据(缓存)")
    @PutMapping("/cache")
    public BaseResponse<DogDO> cacheUpdate(@RequestBody DogDO dogDO) {
        DogDO update = dogService.update(dogDO);
        return BaseResponse.ok(update);
    }

    /**
     * 删除(缓存)
     */
    @ApiOperation("删除(缓存)")
    @DeleteMapping("/cache")
    public BaseResponse<Boolean> cacheDelete(@RequestParam Long id) {
        return BaseResponse.ok(dogService.deleteById(id));
    }
}


