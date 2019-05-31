package com.hnf.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBUtil {
    public static void main(String args[]) {
        try {

            // To connect to mongodb server
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            // Now connect to your databases
            MongoDatabase database = mongoClient.getDatabase("mgdb");

            //database.createCollection("NewCollection",new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));

            MongoCollection<Document> coll = database.getCollection("myTestCollection");

            System.out.println("Collection created successfully");

            System.out.println("当前数据库中的所有集合是：");

            for (String name : database.listCollectionNames()) {
                System.out.println(name);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

    }
}
