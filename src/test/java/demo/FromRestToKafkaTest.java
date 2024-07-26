package demo;

import com.example.kafka.TestMessagesConsumer;
import com.example.rest.requests.AddUserRequest;
import com.example.rest.responses.AddUserResponse;
import com.example.utils.JsonUtils;
import common.TestDataUtils;
import common.BaseTest;
import io.restassured.path.json.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

public class FromRestToKafkaTest extends BaseTest {

    @Autowired
    TestMessagesConsumer consumer;

    @Value("${spring.test-data.user-file}")
    private String userFilePath;

    Map<String, Object> variables = TestDataUtils.getUserVariables();

    AddUserResponse response;
    JsonPath message;

    @BeforeClass
    private void setUp() throws InterruptedException {
        String userJson = JsonUtils.getJsonFromTemplate(userFilePath, variables);
        AddUserRequest request = new AddUserRequest(userJson);
        response = request.send();
        message = consumer.getMessage(response.getId());
    }

    @Test
    public void checkResponseStatusCode(){
        response.then().statusCode(200);
    }

    @Test
    private void userAddedToDB() {
        Assert.assertNotNull(message, "User data message published");
    }

    @Test
    private void checkUserAge() {
        Assert.assertEquals(message.getInt("age"), variables.get("age"), "User age in published message");
    }

    @Test
    private void checkUserFirstName() {
        Assert.assertEquals(message.getString("firstName"), variables.get("firstName"), "User first name in published message");
    }

    @Test
    private void checkUserLastName() {
        Assert.assertEquals(message.getString("lastName"), variables.get("lastName"), "User last name in published message");
    }

}
