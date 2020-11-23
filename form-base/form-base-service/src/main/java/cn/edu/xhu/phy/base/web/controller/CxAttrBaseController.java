package cn.edu.xhu.phy.base.web.controller;

import cn.edu.xhu.phy.base.entity.CxAttrBase;
import cn.edu.xhu.phy.base.service.CxAttrBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;
import java.util.List;

/**
 * @version 1.0
 * @date 2020/11/21 2:23
 */
@RequestMapping("cxAttr")
public class CxAttrBaseController {

    @Autowired
    protected CxAttrBaseService cxAttrBaseService;

    @GetMapping("{id}")
    public CxAttrBase get(@PathVariable Serializable id) {
        return cxAttrBaseService.getById(id);
    }

    @PostMapping
    public Boolean update(@RequestBody CxAttrBase cxAttrBase) {
        return cxAttrBaseService.updateById(cxAttrBase);
    }

    @PutMapping
    public Boolean add(@RequestBody CxAttrBase cxAttrBase) {
        return cxAttrBaseService.save(cxAttrBase);
    }

    @PutMapping("batch")
    public Boolean addBatch(@RequestBody List<CxAttrBase> cxAttrBases) {
        return cxAttrBaseService.saveBatch(cxAttrBases);
    }

    @DeleteMapping("{id}")
    public Boolean delete(@PathVariable Serializable id) {
        return cxAttrBaseService.removeById(id);
    }
}
