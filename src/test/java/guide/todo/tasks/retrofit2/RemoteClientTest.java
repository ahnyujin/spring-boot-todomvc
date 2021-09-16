package guide.todo.tasks.retrofit2;

class RemoteClientTest extends AbstractClientTest {

    @Override
    String getBaseUrl() {
        return "http://localhost:8080";
    }
}
