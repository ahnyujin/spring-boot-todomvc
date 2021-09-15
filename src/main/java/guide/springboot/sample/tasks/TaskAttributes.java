package guide.springboot.sample.tasks;

public class TaskAttributes {
    private final String id;
    private final String details;
    private final String status;

    public TaskAttributes(String id, String details, String status) {
        this.id = id;
        this.details = details;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }
}
