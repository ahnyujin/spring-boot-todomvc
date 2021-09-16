package guide.todo.tasks;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(final TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public UUID insert(final TaskAttributes taskAttributes) {
        final var task = new Task();
        task.setDetails(taskAttributes.getDetails());
        task.setStatus(taskAttributes.getStatus());

        final var storedTask = taskRepository.save(task);

        return storedTask.getId();
    }

    public void delete(final UUID id) {
        taskRepository.deleteById(id);
    }

    public TaskAttributes update(
            final UUID id,
            final TaskAttributes taskAttributes
    ) {
        final var existingTask = taskRepository.findById(id).orElseThrow();

        existingTask.setDetails(taskAttributes.getDetails());
        existingTask.setStatus(taskAttributes.getStatus());

        final var saved = taskRepository.save(existingTask);

        final var savedTaskAttributes = new TaskAttributes();
        savedTaskAttributes.setDetails(saved.getDetails());
        savedTaskAttributes.setStatus(saved.getStatus());
        return savedTaskAttributes;
    }

    public TaskAttributes patch(
            final UUID id,
            final TaskAttributes taskAttributes
    ) {
        final var existingTask = taskRepository.findById(id).orElseThrow();

        if (taskAttributes.getDetails() != null) {
            existingTask.setDetails(taskAttributes.getDetails());
        }
        if (taskAttributes.getStatus() != null) {
            existingTask.setStatus(taskAttributes.getStatus());
        }

        final var saved = taskRepository.save(existingTask);

        final var savedTaskAttributes = new TaskAttributes();
        savedTaskAttributes.setDetails(saved.getDetails());
        savedTaskAttributes.setStatus(saved.getStatus());
        return savedTaskAttributes;
    }

    public Optional<TaskAttributes> select(final UUID taskId) {
        return taskRepository.findById(taskId).map(it -> {
            final var taskAttributes = new TaskAttributes();
            taskAttributes.setDetails(it.getDetails());
            taskAttributes.setStatus(it.getStatus());
            return taskAttributes;
        });
    }

    public List<Task> selectAll() {
        return taskRepository.findAll();
    }
}
