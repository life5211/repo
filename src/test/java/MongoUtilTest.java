
import com.hnf.util.MongoUtil;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Test
    public void contextLoads() {
        MongoCollection<Document> mgdb = MongoUtil.getCollection("test", "test");
//        Document document = new Document("name", "张三")
//                .append("sex", "男")
//                .append("age", 18);
//        mgdb.insertOne(document);
        List<Document> list = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Document document = new Document("name", "张三")
                    .append("sex", "男")
                    .append("age", 18).append("uuid", UUID.randomUUID());
            list.add(document);
        }
        //插入多个文档
        mgdb.insertMany(list);
    }
}