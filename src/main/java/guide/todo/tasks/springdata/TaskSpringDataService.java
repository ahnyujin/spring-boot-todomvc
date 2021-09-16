package guide.todo.tasks.springdata;

import guide.todo.tasks.*;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.*;

import static java.util.Objects.requireNonNullElse;

class TaskSpringDataService implements TaskService {
    private final TaskSpringDataRepository taskSpringDataRepository;

    TaskSpringDataService(final TaskSpringDataRepository taskSpringDataRepository) {
        this.taskSpringDataRepository = taskSpringDataRepository;
    }

    @Override
    public UUID insert(final TaskAttributesInsert taskAttributesInsert) {
        final var taskEntity = new TaskEntity(
                taskAttributesInsert.getDetails(),
                TaskStatus.ACTIVE
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
    public TaskAttributes patch(
            final UUID taskId,
            final TaskAttributesPatch taskAttributesPatch
    ) {
        final var existingTask = taskSpringDataRepository.findById(taskId);
        final var entityToUpdate =
                existingTask.map(it -> new TaskEntity(
                                it.getId(),
                                requireNonNullElse(taskAttributesPatch.getDetails(), it.getDetails()),
                                requireNonNullElse(taskAttributesPatch.getStatus(), it.getStatus())
                        ))
                        .orElseThrow(NoEntityException::new);

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
