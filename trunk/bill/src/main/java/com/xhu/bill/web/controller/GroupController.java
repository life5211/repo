package com.xhu.bill.web.controller;

import com.xhu.bill.bean.GroupBean;
import com.xhu.bill.service.GroupService;
import com.xhu.bill.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.xhu.bill.util.JsonResult.jst;

/**
 * @author user17
 * @version 1.0
 * @date 2019-10-24 0:21
 */
@RestController
@RequestMapping("web/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping("user")
    public JsonResult<List<GroupBean>> getByUserId(Integer id) {
        return jst(groupService.findByUserId(id));
    }

    @GetMapping
    public JsonResult get(Integer page) {
        return null;
    }

    @GetMapping("{id}")
    public JsonResult<GroupBean> getByGroupId(@PathVariable Integer id) {
        return jst(groupService.findOne(id));
    }

    @DeleteMapping("{id}")
    public JsonResult delete(@PathVariable Integer id) {
        return null;
    }

    @PostMapping
    public JsonResult add(GroupBean group) {
        return null;
    }

    @PutMapping
    public JsonResult update(GroupBean group) {
        return null;
    }
}
