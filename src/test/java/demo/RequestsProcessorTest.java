package demo;

import com.example.db.MongoDBService;
import com.example.kafka.TestMessagesConsumer;
import com.example.rest.requests.AddUserRequest;
import com.example.rest.responses.AddUserResponse;
import com.example.utils.JsonUtils;
import com.example.utils.TestDataUtils;
import common.BaseTest;
import io.restassured.path.json.JsonPath;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class RequestsProcessorTest extends BaseTest {

    @Autowired
    TestMessagesConsumer consumer;

    @Autowired
    MongoDBService dbService;

    @Value("${spring.test-data.user-file}")
    private String userFilePath;

    @Value("${spring.mongodb.collection}")
    private String collection;

    Map<String, Object> variables = TestDataUtils.getUserVariables();

    AddUserResponse response;
    Document dbDocument;

    @BeforeClass
    private void setUp(){
        String userJson = JsonUtils.getJsonFromTemplate(userFilePath, variables);
        AddUserRequest request = new AddUserRequest(userJson);
        response = request.send();
        dbDocument = dbService.getDocument(collection, response.getId());
    }

    @Test
    public void checkResponseStatusCode(){
        response.then().statusCode(200);
    }

    @Test
    private void userAddedToDB() {
        Assert.assertNotNull(dbDocument, "User added to DB");
    }

    @Test
    private void checkUserAge() {
        Assert.assertEquals(JsonPath.from(dbDocument.toJson()).getInt("age"), variables.get("age"), "User age in DB");
    }

    @Test
    private void checkUserFirstName() {
        Assert.assertEquals(JsonPath.from(dbDocument.toJson()).getString("firstName"), variables.get("firstName"), "User first name in DB");
    }

    @Test
    private void checkUserLastName() {
        Assert.assertEquals(JsonPath.from(dbDocument.toJson()).getString("lastName"), variables.get("lastName"), "User last name in DB");
    }

}
