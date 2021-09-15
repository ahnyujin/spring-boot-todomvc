package guide.springboot.sample.tasks;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    TaskIdentifier insert(final TaskAttributes attributes);

    Optional<Task> select(final TaskIdentifier identifier);

    List<Task> selectAll();

    Optional<Task> patch(final TaskIdentifier identifier, final TaskAttributes attributes);
}
