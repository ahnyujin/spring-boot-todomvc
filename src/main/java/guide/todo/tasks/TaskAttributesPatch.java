package guide.todo.tasks;

public class TaskAttributesPatch {
    private final String details;
    private final TaskStatus status;

    public TaskAttributesPatch(
            final String details,
            final TaskStatus status
    ) {
        this.details = details;
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
