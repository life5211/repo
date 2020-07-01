package com.xhu.bill.dao;

import com.xhu.bill.bean.BillBean;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 21:12
 */
public interface BillDao {
    /**
     * @param bill
     * @return
     */
    ObjectId insertOne(BillBean bill);

    /**
     * @param id
     * @return
     */
    long deleteOneById(String id);

    /**
     * @param id
     * @return
     */
    BillBean findById(String id);

    /**
     * @param start
     * @param end
     * @param page
     * @param size
     * @return
     */
    List<BillBean> find(Integer group, Long start, Long end, int page, int size);

    /**
     * @return
     */
    long update(BillBean bill);

    /**
     * @param start
     * @param end
     * @return
     */
    long findCount(Integer group, Long start, Long end);

}
