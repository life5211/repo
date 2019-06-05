package com.hnf.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * mongodb 连接数据库工具类
 *
 * @author xuhongzhi
 */

public class MongoUtil {
    private static MongoClient mongoClient;
    private static MongoDatabase mongoDB;
    public static final String DB_NAME = "--";
    public static final String COL_NAME = "";

    /**
     * @return MongoClient
     */
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            mongoClient = new MongoClient();
        }
        return mongoClient;
    }

    public static MongoClient getMongoClient(String host, int port) {
        if (mongoClient == null) {
            mongoClient = new MongoClient(host, port);
        }
        return mongoClient;
    }


    /**
     * //不通过认证获取连接数据库对象
     *
     * @param dbName
     * @return MongoDatabase
     */
    public static MongoDatabase getDBConnect(String dbName) {
        return getMongoClient().getDatabase(dbName);
    }


    /**
     * 需要密码认证方式连接
     *
     * @param username
     * @param source
     * @param password
     * @return
     */
    public static MongoDatabase getDBConnect(String host, int port, String username, String source, String password) {
        if (MongoUtil.mongoClient == null) {
            List<ServerAddress> adds = new ArrayList<>(Arrays.asList(new ServerAddress(host, port)));

            List<MongoCredential> credentials = new ArrayList<>();
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, source, password.toCharArray());
            credentials.add(mongoCredential);

            //通过连接认证获取MongoDB连接
            MongoUtil.mongoClient = new MongoClient(adds, credentials);
        }
        //连接到数据库
        MongoUtil.mongoDB = mongoClient.getDatabase(source);
        //返回连接数据库对象
        return MongoUtil.mongoDB;

    }

    /**
     * @param dataBaseName
     * @param collection
     * @return
     */
    public static MongoCollection<Document> getCollection(String dataBaseName, String collection) {
        return getDBConnect(dataBaseName).getCollection(collection);
    }

    /**
     * 资源关闭
     */
    public static void close() {
        if (MongoUtil.mongoDB != null) {
            MongoUtil.mongoDB = null;
        }
        if (MongoUtil.mongoClient != null) {
            MongoUtil.mongoClient.close();
        }
    }

    public static MongoUtil do1() {
        System.out.println("do1");
        return null;
    }

    public static MongoUtil do2() {
        System.out.println("do2");
        return null;
    }

    public static MongoUtil do3() {
        System.out.println("do3");
        return null;//静态方法链式调用实现方式，能且仅能调用静态方法或者属性。
    }
}
