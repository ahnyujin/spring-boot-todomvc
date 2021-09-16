package guide.todo.tasks.springdata;

import guide.todo.tasks.Task;
import guide.todo.tasks.TaskAttributes;
import guide.todo.tasks.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

class TaskSpringDataService implements TaskService {
    private final TaskSpringDataRepository taskSpringDataRepository;

    TaskSpringDataService(final TaskSpringDataRepository taskSpringDataRepository) {
        this.taskSpringDataRepository = taskSpringDataRepository;
    }

    @Override
    public UUID insert(final TaskAttributes taskAttributes) {
        final var taskEntity = new TaskEntity(
                taskAttributes.getDetails(),
                taskAttributes.getStatus()
        );

        final var savedEntity = taskSpringDataRepository.save(taskEntity);

        return savedEntity.getId();
    }

    @Override
    public void delete(final UUID taskId) {
        try {
            taskSpringDataRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityException(e);
        }
    }

    @Override
    public TaskAttributes update(
            final UUID taskId,
            final TaskAttributes taskAttributes
    ) {
        final var existingTask = taskSpringDataRepository.findById(taskId);
        if (existingTask.isEmpty()) {
            throw new NoEntityException();
        }

        final var entityToUpdate = new TaskEntity(
                taskId,
                taskAttributes.getDetails(),
                taskAttributes.getStatus()
        );

        final var updatedTaskEntity = taskSpringDataRepository.save(entityToUpdate);
        return new TaskAttributes(
                updatedTaskEntity.getDetails(),
                updatedTaskEntity.getStatus()
        );
    }

    @Override
    public Optional<TaskAttributes> select(final UUID taskId) {
        final var taskEntity = taskSpringDataRepository.findById(taskId);

        return taskEntity.map(TaskSpringDataService::toTaskAttributes);
    }

    @Override
    public List<Task> selectAll() {
        final var taskEntities = taskSpringDataRepository.findAll();

        final var tasks = new ArrayList<Task>();
        for (var taskEntity : taskEntities) {
            tasks.add(toTask(taskEntity));
        }
        return Collections.unmodifiableList(tasks);
    }

    static Task toTask(final TaskEntity taskEntity) {
        final var taskId = taskEntity.getId();

        return new Task(
                taskId,
                taskEntity.getDetails(),
                taskEntity.getStatus()
        );
    }

    static TaskAttributes toTaskAttributes(final TaskEntity taskEntity) {
        return new TaskAttributes(
                taskEntity.getDetails(),
                taskEntity.getStatus()
        );
    }
}
