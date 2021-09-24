package guide.springboot.sample.tasks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {

    UUID insert(final TaskAttributesInsert taskAttributesInsert);

    Optional<TaskAttributes> select(final UUID taskId);

    List<Task> selectAll();

    TaskAttributes update(final UUID taskId, final TaskAttributes attributes);

    TaskAttributes patch(final UUID taskId, final TaskAttributesPatch attributesPatch);

    void delete(final UUID taskId);

    class NoEntityException extends RuntimeException {

        public NoEntityException() {
            super();
        }

        public NoEntityException(final Throwable e) {
            super(e);
        }

        public NoEntityException(final String message) {
            super(message);
        }
    }
}
