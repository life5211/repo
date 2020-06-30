package com.xhu.bill.daoimpl;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-19 18:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BillDaoTest {
    @Autowired
    BillDao billDao;

    @Test
    public void save() {
        BillBean billBean = new BillBean();
        ObjectId save = billDao.insertOne(billBean);
        System.out.println(save);
    }

    @Test
    public void delete() {
        System.out.println(billDao.deleteOneById("5d8380a2e87b5103b0858f9a"));
    }

    @Test
    public void find() {
//        System.out.println(billDao.find(null, null, 1, 50));
    }

    @Test
    public void update() {
    }

    @Test
    public void findCount() {
//        System.out.println(billDao.findCount(0L, Long.MAX_VALUE));
    }
}