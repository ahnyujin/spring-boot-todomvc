package guide.todo.tasks;

import static java.util.Objects.requireNonNull;

public class TaskAttributes {
    private final String details;
    private final TaskStatus status;

    public TaskAttributes(
            final String details,
            final TaskStatus status
    ) {
        this.details = requireNonNull(details);
        this.status = requireNonNull(status);
    }

    public String getDetails() {
        return details;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
