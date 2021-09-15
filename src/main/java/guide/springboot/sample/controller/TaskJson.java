package guide.springboot.sample.controller;

public class TaskJson extends TaskIdentifierJson {
    private final String details;
    private final String status;

    TaskJson(final String id, final String details, final String status) {
        super(id);
        this.details = details;
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }
}
