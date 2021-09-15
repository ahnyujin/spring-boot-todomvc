package guide.springboot.sample.tasks.springdata;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    private String id;
    private String details;
    private String status;

    protected TaskEntity() {
    }

    public TaskEntity(String id, String details, String status) {
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
