package com.tydic.work.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tydic.work.entity.WorkLog;
import com.tydic.work.service.WorkLogService;
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
 * (WorkLog)表控制层
 *
 * @author makejava
 * @since 2021-01-18 16:54:39
 */
@RestController
@RequestMapping("workLog")
public class WorkLogController extends ApiController {
    /**
     * 服务对象
     */
    @Resource
    private WorkLogService workLogService;

    /**
     * 分页查询所有数据
     *
     * @param page    分页对象
     * @param workLog 查询实体
     * @return 所有数据
     */
    @GetMapping
    public R selectAll(Page<WorkLog> page, WorkLog workLog) {
        return success(this.workLogService.page(page, new QueryWrapper<>(workLog)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public R selectOne(@PathVariable Serializable id) {
        return success(this.workLogService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param workLog 实体对象
     * @return 新增结果
     */
    @PostMapping
    public R insert(@RequestBody WorkLog workLog) {
        return success(this.workLogService.save(workLog));
    }

    /**
     * 新增数据
     *
     * @param workLogs 实体对象
     * @return 新增结果
     */
    @PostMapping("batch")
    public R insertBatch(@RequestBody List<WorkLog> workLogs) {
        return success(this.workLogService.saveBatch(workLogs));
    }

    /**
     * 修改数据
     *
     * @param workLog 实体对象
     * @return 修改结果
     */
    @PutMapping
    public R update(@RequestBody WorkLog workLog) {
        return success(this.workLogService.updateById(workLog));
    }

    /**
     * 删除数据
     *
     * @param idList 主键结合
     * @return 删除结果
     */
    @DeleteMapping
    public R delete(@RequestParam("idList") List<Long> idList) {
        return success(this.workLogService.removeByIds(idList));
    }
}