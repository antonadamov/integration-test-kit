import com.example.config.TestConfig;
import com.example.kafka.TestMessageProducer;
import com.example.kafka.TestMessagesConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.UUID;

@SpringBootTest(classes = TestConfig.class)
public class BaseTest extends AbstractTestNGSpringContextTests {

    @Autowired
    TestMessageProducer producer;

    @Autowired
    TestMessagesConsumer consumer;

    @Test
    public void test(){
        String message = "Test Message";
        String key = UUID.randomUUID().toString();
        producer.sendMessage(key, message);
        String consumedMessage = consumer.getMessage(key);
        Assert.assertNotNull(consumedMessage, "Message consumed");
        Assert.assertEquals(consumedMessage, message, "Consumed message text");
    }
}
