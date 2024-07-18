import com.example.config.TestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

@SpringBootTest(classes = TestConfig.class)
public class BaseTest extends AbstractTestNGSpringContextTests {
}
