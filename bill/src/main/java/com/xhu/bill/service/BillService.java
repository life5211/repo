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
    JsonResult<List<BillBean>> findBills(Integer group, int page, Long start, Long end);

    /**
     * @param id
     * @return
     */
    JsonResult deleteOneById(String id);

    /**
     * @param bill
     * @return
     */
    JsonResult insertOne(BillBean bill);

    /**
     * @param id
     * @return
     */
    JsonResult<BillBean> findById(String id);

    /**
     * @param bill
     * @return
     */
    JsonResult updateOne(BillBean bill);
}
