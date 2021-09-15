package guide.springboot.sample.controller;

public class TaskIdentifierJson {
    private final String id;

    TaskIdentifierJson(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
