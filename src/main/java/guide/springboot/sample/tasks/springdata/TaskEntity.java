package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.tasks.TaskStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    private String id;
    private String details;
    private TaskStatus status;

    protected TaskEntity() {
    }

    public TaskEntity(final String id, final String details, final TaskStatus status) {
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

    public TaskStatus getStatus() {
        return status;
    }
}
