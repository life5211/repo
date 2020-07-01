package com.hnf.util;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.InsertManyOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * MongoDB 的连接类
 *
 * @author hnf_bigbee
 */
public class MongoUtils {
    private static final MongoClient MONGO_CLIENT = new MongoClient("localhost", 27017);

    /**
     * 执行对应的入库操作
     *
     * @param doc 对应的入库条件
     */
    public static void insertAlipayTransactionTable(List<Document> doc) {
        MongoCollection<Document> collection =
                MONGO_CLIENT.getDatabase("Neo4jRelation").getCollection("AlipayTransaction");
        try {
            collection.insertMany(doc, new InsertManyOptions().ordered(false));
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 执行对应人员入库操作
     *
     * @param doc
     */
    public static void insertPerson(List<Document> doc) {
        MongoCollection<Document> collection =
                MONGO_CLIENT.getDatabase("infoData2").getCollection("t_person");
        try {
            collection.insertMany(doc, new InsertManyOptions().ordered(false));
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }

    /**
     * 执行中间表入库操作
     *
     * @param doc
     */
    public static void insertAlipayUserInfoTable(List<Document> doc) {
        MongoCollection<Document> collection =
                MONGO_CLIENT.getDatabase("Neo4jRelation").getCollection("AlipayUserInfo");
        try {
            collection.insertMany(doc, new InsertManyOptions().ordered(false));
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void insertCurrentAnalysisPersonTable(List<Document> doc) {
        MongoCollection<Document> collection =
                MONGO_CLIENT.getDatabase("simpleAnalysis").getCollection("t_current_analysis_person");
        try {
            collection.insertMany(doc, new InsertManyOptions().ordered(false));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 对数据库进行聚合
     *
     * @param dbName
     * @param collectionName
     * @param startTime
     * @param endTime
     * @param cnt
     * @param startNumber
     * @param endNumber
     */
    public static List<Document> aggregateInfoByQyery(String dbName, String collectionName, Long startTime,
                                                      Long endTime,
                                                      Integer cnt, HashSet<String> startNumber,
                                                      HashSet<String> endNumber) {
        if (null == startNumber || startNumber.isEmpty()) {
            throw new RuntimeException("开始节点为NULL");
        }
        if (null == endNumber || endNumber.isEmpty()) {
            throw new RuntimeException("结束节点为NULL");
        }
        List<Document> result = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        BasicDBObject havingField = new BasicDBObject();
        query.append("deviceName", new BasicDBObject(QueryOperators.IN, startNumber));
        query.append("uin", new BasicDBObject(QueryOperators.IN, endNumber));
        MongoCollection<Document> collection = MONGO_CLIENT.getDatabase(dbName).getCollection(collectionName);
        BasicDBObject timeQuery = new BasicDBObject();
        if (startTime != null) {
            timeQuery.append("$gte", startTime);
        }
        if (endTime != null) {
            timeQuery.append("$lte", endTime);
        }
        if (!timeQuery.isEmpty()) {
            query.append("time", timeQuery);
        }
        if (cnt != null) {
            havingField.append("count", new BasicDBObject(QueryOperators.GTE, cnt));
        }
        BasicDBObject match = new BasicDBObject("$match", query);
        BasicDBObject group = new BasicDBObject("$group", new BasicDBObject("_id",
                new BasicDBObject("deviceName", "$deviceName").append("uin", "$uin")
        ).append("count", new BasicDBObject("$sum", 1)));
        BasicDBObject having = new BasicDBObject("$match", havingField);
        AggregateIterable<Document> aggregate = collection.aggregate(Arrays.asList(match, group, having));
        for (Document doc : aggregate) {
            result.add(doc);
        }
        return result;
    }
}
