package com.xhu.bill.daoimpl;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.QueryOperators;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xhu.bill.bean.BillBean;
import com.xhu.bill.dao.BillDao;
import com.xhu.bill.dao.MongoDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author user17
 * @version 1.0
 * @date 2019-9-16 22:43
 */
@Repository
public class BillDaoImpl implements BillDao {
    @Autowired
    private MongoDao mongoDao;

    @Override
    public ObjectId insertOne(BillBean bill) {
        Document document = new Document(JSONObject.parseObject(JSONObject.toJSONString(bill)));
        mongoDao.connect("bill", "bill").insertOne(document);
        bill.setId(document.getObjectId("_id").toString());
        return document.getObjectId("_id");
    }

    @Override
    public long deleteOneById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        DeleteResult deleteResult = mongoDao.connect("bill", "bill").deleteOne(query);
        return deleteResult.getDeletedCount();
    }

    @Override
    public BillBean findById(String id) {
        Document query = new Document("_id", new ObjectId(id));
        List<BillBean> list = mongoDao.toList(
                mongoDao.connect("bill", "bill").find(query),
                BillBean.class);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<BillBean> find(Integer group, Long start, Long end, int page, int size) {
        Document query = getQuery(group, start, end);
        Document sort = new Document("recordTime", -1);
        return mongoDao.toList(
                mongoDao.connect("bill", "bill").find(query).sort(sort).skip(page * size - size).limit(size)
                , BillBean.class);
    }

    @Override
    public long update(BillBean bill) {
        UpdateResult updateResult =
                mongoDao.connect("bill", "bill").updateOne(
                        new Document("_id", new ObjectId(bill.getId()))
                        , new Document()
                );
        return updateResult.getModifiedCount();
    }

    @Override
    public long findCount(Integer group, Long start, Long end) {
        Document query = getQuery(group, start, end);
        return mongoDao.connect("bill", "bill").countDocuments(query);
    }

    private Document getQuery(Integer group, Long start, Long end) {
        Document query = new Document();
        if (start != null || end != null) {
            Document time = new Document();
            if (start != null) {
                time.append(QueryOperators.GT, start);
            }
            if (end != null) {
                time.append(QueryOperators.LTE, end);
            }
            query.append("recordTime", time);
        }
        query.append("group", group);
        return query;
    }
}
