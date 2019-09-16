package com.xhu.bill;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.xhu.bill.util.JcfUtil;
import com.xhu.bill.util.JsonResult;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BillApplicationTests {

    @Autowired
    MongoClient mongoClient;

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void contextLoads() {
        MongoCollection<Document> collection = mongoClient.getDatabase("bill").getCollection("bill");
        collection.insertOne(new Document("test", "test"));
        List<Document> documents = JcfUtil.toList(collection.find());
        documents.forEach(System.out::println);
        List<Document> bill = JcfUtil.toList(mongoTemplate.getCollection("bill").find());
        bill.forEach(System.out::println);

        Map<String, Object> t = JcfUtil.ofMap("", (Object) "").put("t", "").builder();
        System.out.println(t);
    }

}
