package com.xhu.bill.web.controller;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.service.BillService;
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

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 21:53
 */
@RestController
@RequestMapping("web/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @GetMapping
    public JsonResult<List<BillBean>> getBills(Integer group, Integer page, Long start, Long end) {
        return JsonResult.jst(billService.findBills(group, page, start, end));
    }

    @GetMapping("{id}")
    public JsonResult<BillBean> getBillById(@PathVariable String id) {
        return JsonResult.jst(billService.findById(id));
    }

    @PostMapping
    public JsonResult<BillBean> addBill(BillBean bill) {
        return billService.insertOne(bill);
    }

    @DeleteMapping
    public JsonResult deleteOneById(@PathVariable String id) {
        return JsonResult.jst(billService.deleteOneById(id));
    }

    @PutMapping
    public JsonResult updateOne(BillBean bill) {
        return JsonResult.jst(billService.updateOne(bill));
    }

}
