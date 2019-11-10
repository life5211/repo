package com.xhu.bill.service;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.util.JsonResult;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-20 9:45
 */
public interface BillService {
    /**
     * @param page
     * @param start
     * @param end
     * @return
     */
    List<BillBean> findBills(Integer group, int page, Long start, Long end);

    /**
     * @param id
     * @return
     */
    int deleteOneById(String id);

    /**
     * @param bill
     * @return
     */
    JsonResult<BillBean> insertOne(BillBean bill);

    /**
     * @param id
     * @return
     */
    BillBean findById(String id);

    /**
     * @param bill
     * @return
     */
    BillBean updateOne(BillBean bill);
}
