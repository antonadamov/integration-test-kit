package demo;

import com.example.db.DbDocument;
import com.example.db.MongoDBService;
import com.example.kafka.TestMessageProducer;
import com.example.utils.JsonUtils;
import com.example.utils.TestDataUtils;
import common.BaseTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class FromKafkaToDbTest extends BaseTest {

    @Autowired
    MongoDBService dbService;

    @Autowired
    TestMessageProducer producer;

    @Value("${spring.test-data.user-file}")
    private String userFilePath;

    @Value("${spring.mongodb.collection}")
    private String collection;

    Map<String, Object> variables = TestDataUtils.getUserVariables();

    DbDocument dbDocument;

    @BeforeClass
    private void setUp(){
        String userJson = JsonUtils.getJsonFromTemplate(userFilePath, variables);
        producer.sendMessage(userJson);
        dbDocument = dbService.waitForDocument(collection, variables);
    }


    @Test
    private void userAddedToDB() {
        Assert.assertNotNull(dbDocument, "User added to DB");
    }

    @Test
    private void checkUserAge() {
        Assert.assertEquals(dbDocument.toJsonPath().getInt("age"), variables.get("age"), "User age in DB");
    }

    @Test
    private void checkUserFirstName() {
        Assert.assertEquals(dbDocument.toJsonPath().getString("firstName"), variables.get("firstName"), "User first name in DB");
    }

    @Test
    private void checkUserLastName() {
        Assert.assertEquals(dbDocument.toJsonPath().getString("lastName"), variables.get("lastName"), "User last name in DB");
    }

}
