package com.hnf.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * //mongodb 连接数据库工具类
 */
public class MongoUtil {
    private static MongoDatabase mongoDatabase;
    private static MongoCollection<Document> mongoCollection;


    /**
     * //不通过认证获取连接数据库对象
     *
     * @param dataBaseName
     * @return
     */
    public static MongoDatabase getConnect(String dataBaseName) {
        if (MongoUtil.mongoDatabase == null) {
            //连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient();
            //连接到数据库
            MongoUtil.mongoDatabase = mongoClient.getDatabase(dataBaseName);
        }
        //返回连接数据库对象
        return MongoUtil.mongoDatabase;
    }


    /**
     * 需要密码认证方式连接
     *
     * @param username
     * @param source
     * @param password
     * @return
     */
    public static MongoDatabase getConnect(String username, String source, String password) {
        if (MongoUtil.mongoDatabase == null) {
            List<ServerAddress> adds = new ArrayList<>();
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("localhost", 27017);
            adds.add(serverAddress);

            List<MongoCredential> credentials = new ArrayList<>();
            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(username, source, password.toCharArray());
            credentials.add(mongoCredential);

            //通过连接认证获取MongoDB连接
            MongoClient mongoClient = new MongoClient(adds, credentials);

            //连接到数据库
            MongoUtil.mongoDatabase = mongoClient.getDatabase(source);
        }
        //返回连接数据库对象
        return MongoUtil.mongoDatabase;

    }

    public static MongoCollection<Document> getCollection(String dataBaseName, String col) {

        return getConnect(dataBaseName).getCollection(col);
    }
}
