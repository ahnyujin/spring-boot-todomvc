package guide.springboot.sample.tasks;

import java.util.Objects;

public class Task {
    private final TaskIdentifier identifier;
    private final String details;
    private final TaskStatus status;

    public Task(TaskIdentifier identifier, String details, TaskStatus status) {
        this.identifier = identifier;
        this.details = details;
        this.status = status;
    }

    public TaskIdentifier getIdentifier() {
        return identifier;
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
        return Objects.equals(identifier, task.identifier) && Objects.equals(details, task.details) && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, details, status);
    }
}
