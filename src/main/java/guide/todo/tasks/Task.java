package guide.todo.tasks;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Task {
    private final UUID id;
    private final String details;
    private final TaskStatus status;

    public Task(
            final UUID id,
            final String details,
            final TaskStatus status) {
        this.id = requireNonNull(id);
        this.details = requireNonNull(details);
        this.status = requireNonNull(status);
    }

    public UUID getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public TaskStatus getStatus() {
        return status;
    }
}