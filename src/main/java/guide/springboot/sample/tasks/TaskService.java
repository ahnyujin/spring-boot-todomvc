package guide.springboot.sample.tasks;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskIdentifier insert(final TaskAttributesInsert taskAttributesInsert);

    Optional<Task> select(final TaskIdentifier identifier);

    List<Task> selectAll();

    Task update(final TaskIdentifier identifier, final TaskAttributes attributes);

    Optional<Task> patch(final TaskIdentifier identifier, final TaskAttributesPatch attributes);

    void delete(final TaskIdentifier identifier);
}
