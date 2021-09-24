package guide.springboot.sample.controller;

import static java.util.Objects.requireNonNull;

public class TaskIdResponse {
    private final String id;

    TaskIdResponse(final String id) {
        this.id = requireNonNull(id);
    }

    public String getId() {
        return id;
    }
}
