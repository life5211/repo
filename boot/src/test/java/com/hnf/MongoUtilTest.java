package com.hnf;

import com.hnf.util.MongoUtil;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

/**
 * desc:
 * Created on 2017/10/13.
 *
 * @author Lo_ading
 * @version 1.0.0
 * @since 1.0.0
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//@Rollback(true)
public class MongoUtilTest {
    @MockBean
    MongoCollection<Document> collection;
    private MongoCollection<Document> col;


    @Before
    public void connect() {
        col = MongoUtil.getCollection("test", "test");
    }

    @Before
    public void setUp() {
        collection = MongoUtil.getCollection("test", "test");
    }

    /**
     * 新增一条数据
     */
    @Test
    public void insertOne() {
        Document document = new Document("name", "张三")
                .append("sex", "男")
                .append("age", 18);
        collection.insertOne(document);
    }

    /**
     * 批量删除
     */
    @Test
    public void deleteMany() {

        collection.deleteMany(all("age", 18));
    }

    /**
     * 批量插入
     */
    @Test
    public void insertMany() {
        List<Document> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Document document = new Document("name", "张三")
                    .append("sex", "男")
                    .append("age", 18);
            list.add(document);
        }
        collection.insertMany(list);
    }

    /**
     * 更新一个
     */
    @Test
    public void updateOne() {
        Document document = new Document("$unset", new Document("age", 0));
        collection.updateOne(Filters.eq("name", "张三1"), document);
    }

    /**
     * 更新多个
     */
    @Test
    public void updateMany() {
        Document document = new Document("$inc", new Document("age", -51));
        collection.updateMany(Filters.eq("name", "张三1"), document);
    }

    /**
     * 查找
     */
    @Test
    public void find() {
        FindIterable findIterable = /*collection.find(Filters.eq("name", "张三1"));*/


                collection.find(and(gte("age", 21), Filters.lt("age", 100)))
                        .projection(new Document("name", 1)
                                .append("sex", 1)
                                .append("categories", 1)
                                .append("_id", 0));
        print(findIterable);
    }

    @Test
    public void find11() {
        FindIterable findIterable = /*collection.find(Filters.eq("name", "张三1"));*/


                collection.find(in("age",Arrays.asList(null,24,32,21,53)))
                        ;
        print(findIterable);
    }

    /**
     * 查找
     */
    @Test
    public void find1() {
//        FindIterable findIterable =
        AggregateIterable<Document> aggregate = collection.aggregate(
                Arrays.asList(
                        /*Aggregates.match(eq("categories", "Bakery")),*/
                        Aggregates.group("$sex", Accumulators.sum("count", 1))
                )
        );
        print(aggregate);
    }


    void print(Iterable findIterable) {
        for (Object o : findIterable) {
            System.err.println(JSON.serialize(o));
        }
    }

    @After
    public void showAll() {
        for (Document document : col.find()) {
            System.out.println((document));
        }
    }

    @Test
    public void m1() {
        System.out.println(MongoUtil.do1().do2().do3().MONGO_UTIL.DB_NAME);
        col.updateMany(Filters.exists("title"), new Document("$unset", new Document("likes", 200)));
    }
}







