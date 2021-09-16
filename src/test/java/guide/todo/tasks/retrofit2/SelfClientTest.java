package guide.todo.tasks.retrofit2;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SelfClientTest extends AbstractClientTest {

    @RegisterExtension
    SpringExtension springExtension = new SpringExtension();

    @LocalServerPort
    private int port;

    @Override
    String getBaseUrl() {
        return "http://localhost:" + port;
    }
}
