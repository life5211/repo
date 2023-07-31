package com.tydic.work.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tydic.work.entity.Attr;
import com.tydic.work.service.AttrService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * (Attr)表控制层
 *
 * @author makejava
 * @since 2021-01-18 16:49:13
 */
@RestController
@RequestMapping("attr")
public class AttrController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private AttrService attrService;

    @GetMapping("{code}")
    public R getAttrs(@PathVariable String code) {
        return success(this.attrService.getAttrs(code));
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param attr 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<Attr> page, Attr attr) {
        return success(this.attrService.page(page, new QueryWrapper<>(attr)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("detail/{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.attrService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param attr 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody Attr attr) {
        return success(this.attrService.save(attr));
    }

    /**
     * 修改数据
     *
     * @param attr 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody Attr attr) {
        return success(this.attrService.updateById(attr));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.attrService.removeByIds(idList));
    }
}