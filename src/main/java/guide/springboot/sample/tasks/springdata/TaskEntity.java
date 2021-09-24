package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.tasks.TaskStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String details;
    private TaskStatus status;

    protected TaskEntity() {
    }

    TaskEntity(final String details, final TaskStatus status) {
        this(null, details, status);
    }

    TaskEntity(final UUID id, final String details, final TaskStatus status) {
        this.id = id;
        this.details = details;
        this.status = status;
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
