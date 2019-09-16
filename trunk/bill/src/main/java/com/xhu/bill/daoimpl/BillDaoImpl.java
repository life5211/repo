package com.xhu.bill.daoimpl;

import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import com.xhu.bill.dao.MongoDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 22:43
 */
public class BillDaoImpl implements BillDao {
    @Autowired
    private MongoDao mongoDao;

    @Override
    public ObjectId save(BillBean bill) {
        Document document = new Document("desc", bill.getDesc())
                .append("amount", bill.getAmount());
        mongoDao.connect("bill", "bill").insertOne(document);
        return null;
    }

    @Override
    public int delete() {
        return 0;
    }

    @Override
    public List<BillBean> find(int start, int end) {
        return null;
    }

    @Override
    public int update() {
        return 0;
    }

    @Override
    public int findCount(int start, int end) {
        return 0;
    }
}
