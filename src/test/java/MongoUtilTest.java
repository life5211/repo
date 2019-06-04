
import com.hnf.util.MongoDBUtil;
import com.hnf.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        // MongoCollection<Document> collection = MongoDBUtil.getDBConnect().getCollection("user");
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

        collection.deleteMany(Filters.all("age", 18));
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
        Document document = new Document("$set", new Document("age", 100));
        collection.updateOne(Filters.eq("name", "张三"), document);
    }

    /**
     * 更新多个
     */
    @Test
    public void updateMany() {
        Document document = new Document("$inc", new Document("age", -51));
        collection.updateMany(Filters.eq("name", "张三"), document);
    }

    /**
     * 查找
     */
    @Test
    public void find() {
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
//            Document document = (Document) cursor.next();
//            System.out.println(document.get("name"));
             System.out.println(cursor.next());
        }
    }

    @After
    public void showAll() {
        for (Document document : col.find()) {
            System.err.println(document);
        }
    }

    @Test
    public void m1() {
        col.updateMany(Filters.exists("uuid"), new Document("$set", new Document("likes", 200)));

    }
}