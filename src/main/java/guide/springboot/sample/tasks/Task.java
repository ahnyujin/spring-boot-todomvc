package guide.springboot.sample.tasks;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class Task {
    private final UUID id;
    private final String details;
    private final TaskStatus status;

    public Task(final UUID id, final String details, final TaskStatus status) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(details, task.details) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, details, status);
    }
}
