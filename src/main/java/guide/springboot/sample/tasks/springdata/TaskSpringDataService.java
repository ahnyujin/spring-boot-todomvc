package guide.springboot.sample.tasks.springdata;

import guide.springboot.sample.lang.UuidGenerator;
import guide.springboot.sample.tasks.Task;
import guide.springboot.sample.tasks.TaskAttributes;
import guide.springboot.sample.tasks.TaskIdentifier;
import guide.springboot.sample.tasks.TaskService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskSpringDataService implements TaskService {
    private static final Logger logger = LoggerFactory.getLogger(TaskServiceConfig.class);

    private final TaskSpringDataRepository taskSpringDataRepository;
    private final UuidGenerator uuidGenerator;


    @Override
    public TaskIdentifier insert(TaskAttributes attributes) {
        final var id = uuidGenerator.generateUuidString();

        final var entity = new TaskEntity(Optional.ofNullable(attributes.getId()).orElse(id), attributes.getDetails(), Optional.ofNullable(attributes.getStatus()).orElse("active"));

        final var saved = taskSpringDataRepository.save(entity);

        return new TaskIdentifier(saved.getId());
    }

    @Override
    public Optional<Task> select(TaskIdentifier identifier) {
        final var id = identifier.getValue();

        return taskSpringDataRepository.findById(id)
                .map(TaskSpringDataService::toService);
    }

    @Override
    public List<Task> selectAll() {

        final var tasks = taskSpringDataRepository.findAll();

        return tasks.stream()
                .map(TaskSpringDataService::toService)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Task update(TaskIdentifier identifier, TaskAttributes attributes) {

        final var id = identifier.getValue();
        final var existingTask = taskSpringDataRepository.findById(id).orElseThrow();

        final var entityToUpdate = new TaskEntity(id, attributes.getDetails(), attributes.getStatus());
        final var updatedTaskEntity = taskSpringDataRepository.save(entityToUpdate);
        return toService(updatedTaskEntity);
    }

    @Override
    public Optional<Task> patch(TaskIdentifier identifier, TaskAttributes attributes) {

        final var id = identifier.getValue();
        final Optional<TaskEntity> task = taskSpringDataRepository.findById(id);
        final Optional<TaskEntity> patchedTask = task.map(x -> new TaskEntity(id, Optional.ofNullable(attributes.getDetails()).orElse(x.getDetails()), Optional.ofNullable(attributes.getStatus()).orElse(x.getStatus())));

        patchedTask.ifPresent(o -> taskSpringDataRepository.save(o));
        return patchedTask.map(TaskSpringDataService::toService);
    }

    @Override
    public void delete(TaskIdentifier identifier) {

        final var id = identifier.getValue();
        try {
            taskSpringDataRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.info("[task delete] No task Entity of id=" + id);
        }
    }

    TaskSpringDataService(final TaskSpringDataRepository taskSpringDataRepository, UuidGenerator uuidGenerator) {
        this.taskSpringDataRepository = taskSpringDataRepository;
        this.uuidGenerator = uuidGenerator;
    }

    static Task toService(final TaskEntity entity) {
        final var identifier = new TaskIdentifier(entity.getId());
        return new Task(identifier, entity.getDetails(), entity.getStatus());
    }
}
