package com.xhu.bill.daoimpl;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import com.xhu.bill.util.JcfUtil;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
        BillBean test = new BillBean().setAmount("100.00").setDesc("test").setGroup(1)
                .setSpeedTime(System.currentTimeMillis()).setDesc("desc");
        test.setCreateTime(System.currentTimeMillis());

        test.setInvestor(JcfUtil.ofMap("yds", "50.00").put("wh", "50.00").build());
        test.setConsumers(JcfUtil.ofMap("wsf", "50.00").put("xhz", "50.00").build());

        ObjectId save = billDao.insertOne(test);
        System.out.println(save);
    }

    @Test
    public void delete() {
        System.out.println(billDao.deleteOneById("5d8380a2e87b5103b0858f9a"));
    }

    @Test
    public void find() {
        List<BillBean> billBeans = billDao.find(1, 2L, 15703469952582L, 1, 50);
        System.out.println(billBeans);
    }

    @Test
    public void update() {
    }

    @Test
    public void findCount() {
//        System.out.println(billDao.findCount(0L, Long.MAX_VALUE));
    }
}