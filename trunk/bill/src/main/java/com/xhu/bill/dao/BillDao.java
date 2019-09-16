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
    ObjectId save(BillBean bill);

    /**
     * @return
     */
    int delete();

    /**
     * @param start
     * @param end
     * @return
     */
    List<BillBean> find(int start, int end);

    /**
     * @return
     */
    int update();

    /**
     * @param start
     * @param end
     * @return
     */
    int findCount(int start, int end);

}
