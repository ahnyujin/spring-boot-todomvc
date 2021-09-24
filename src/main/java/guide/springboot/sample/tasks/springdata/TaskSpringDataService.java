package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.tasks.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;

public class TaskSpringDataService implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceConfig.class);

    private final TaskSpringDataRepository taskSpringDataRepository;

    TaskSpringDataService(final TaskSpringDataRepository taskSpringDataRepository) {
        this.taskSpringDataRepository = taskSpringDataRepository;
    }

    @Override
    public UUID insert(final TaskAttributesInsert taskAttributesInsert) {
        final var taskEntity = new TaskEntity(taskAttributesInsert.getDetails(), TaskStatus.ACTIVE);

        final var saved = taskSpringDataRepository.save(taskEntity);

        return saved.getId();
    }

    @Override
    public Optional<TaskAttributes> select(final UUID taskId) {
        return taskSpringDataRepository.findById(taskId)
                .map(TaskSpringDataService::toTaskAttributes);
    }

    @Override
    public List<Task> selectAll() {

        final var tasks = taskSpringDataRepository.findAll();

        return tasks.stream()
                .map(TaskSpringDataService::toTask)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public TaskAttributes update(final UUID taskId, TaskAttributes attributes) {

        final var existingTask = taskSpringDataRepository.findById(taskId);
        if(existingTask.isEmpty()){
            throw new NoEntityException();
        }
        final var entityToUpdate = new TaskEntity(taskId, attributes.getDetails(), attributes.getStatus());
        final var updatedTaskEntity = taskSpringDataRepository.save(entityToUpdate);
        return toTaskAttributes(updatedTaskEntity);
    }

    @Override
    public TaskAttributes patch(final UUID taskId, final TaskAttributesPatch taskAttributesPatch) {

        final var existingTask = taskSpringDataRepository.findById(taskId);
        final var entityToUpdate = existingTask.map(it -> new TaskEntity(it.getId(), requireNonNullElse(taskAttributesPatch.getDetails(), it.getDetails()),
                requireNonNullElse(taskAttributesPatch.getStatus(), it.getStatus()))).orElseThrow();

        final var updatedTaskEntity = taskSpringDataRepository.save(entityToUpdate);
        return new TaskAttributes(updatedTaskEntity.getDetails(), updatedTaskEntity.getStatus());
    }

    @Override
    public void delete(final UUID taskId) {

        try {
            taskSpringDataRepository.deleteById(taskId);
        } catch (EmptyResultDataAccessException e) {
            throw new NoEntityException(e);
        }
    }



    static Task toTask(final TaskEntity taskEntity) {
        final var taskId = taskEntity.getId();
        return new Task(taskId, taskEntity.getDetails(), taskEntity.getStatus());
    }

    static TaskAttributes toTaskAttributes(final TaskEntity taskEntity) {
        return new TaskAttributes(
                taskEntity.getDetails(),
                taskEntity.getStatus()
        );
    }
}
