// package com.xhu.bill.controller;
//
// import com.xhu.bill.bean.BillBean;
// import com.xhu.bill.service.BillService;
// import com.xhu.bill.util.JsonResult;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import java.util.List;
//
// /**
//  * @author user17
//  * @version 1.0
//  * @date 2019-9-16 21:53
//  */
// @RestController
// @RequestMapping("bill/")
// public class BillController {
//
//     @Autowired
//     private BillService billService;
//
//     @GetMapping("bill")
//     public List<BillBean> getBills(Integer group, Integer page, Long start, Long end) {
//         return billService.findBills(group, page, start, end);
//     }
//
//     @GetMapping("bill/{id}")
//     public BillBean getBillById(@PathVariable String id) {
//         return billService.findById(id);
//     }
//
//     @PostMapping("bill")
//     public Object addBill(BillBean bill) {
//         return billService.insertOne(bill);
//     }
//
//     @DeleteMapping("bill/{id}")
//     public Object deleteOneById(@PathVariable String id) {
//         return billService.deleteOneById(id);
//     }
//
//     @PutMapping("bill")
//     public Object updateOne(BillBean bill) {
//         return billService.updateOne(bill);
//     }
//
// }
