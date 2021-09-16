package guide.todo.tasks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    UUID insert(TaskAttributes taskAttributes);

    void delete(final UUID taskId);

    TaskAttributes update(UUID taskId, TaskAttributes taskAttributes);

    Optional<TaskAttributes> select(UUID taskId);

    List<Task> selectAll();

    class NoEntityException extends RuntimeException {

        public NoEntityException() {
            super();
        }

        public NoEntityException(Throwable e) {
            super(e);
        }
    }
}
