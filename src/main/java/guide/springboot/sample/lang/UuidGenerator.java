package guide.springboot.sample.lang;

import java.util.UUID;

public class UuidGenerator {

    public String generateUuidString() {
        return UUID.randomUUID().toString();
    }
}
