package guide.todo.tasks.springdata;

import guide.todo.tasks.TaskStatus;

import javax.persistence.*;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

@Entity
@Table(name = "tasks")
class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String details;
    private TaskStatus status;

    protected TaskEntity() {
    }

    public TaskEntity(
            final String details,
            final TaskStatus status
    ) {
        this(null, details, status);
    }

    public TaskEntity(
            final UUID id,
            final String details,
            final TaskStatus status
    ) {
        this.id = id;
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

    public void setDetails(String details) {
        this.details = details;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
