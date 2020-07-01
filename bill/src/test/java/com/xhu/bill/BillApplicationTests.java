package com.xhu.bill;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.xhu.bill.bean.BillBean;
import com.xhu.bill.util.JcfUtil;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        Map<String, Object> t = JcfUtil.ofMap("", (Object) "").put("t", "").build();
        System.out.println(t);

        List<BillBean> list = new ArrayList<>();
        list.stream().collect(Collectors.toMap(BillBean::getInvestor, a -> a));

        final Map<String, List<BillBean>> collect = list.stream().collect(Collectors.groupingBy(BillBean::getAmount));
    }

}
