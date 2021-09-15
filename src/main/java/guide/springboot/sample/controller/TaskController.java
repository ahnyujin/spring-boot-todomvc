package guide.springboot.sample.controller;

import guide.springboot.sample.lang.validation.Uuid;
import guide.springboot.sample.tasks.Task;
import guide.springboot.sample.tasks.TaskAttributes;
import guide.springboot.sample.tasks.TaskIdentifier;
import guide.springboot.sample.tasks.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    List<TaskJson> retrieveAll() {
        final var tasks = taskService.selectAll();

        return tasks.stream()
                .map(TaskController::toJson)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskJson> retrieveById(@Uuid @PathVariable("id") final String id) {
        final var identifier = new TaskIdentifier(id);

        final var task = taskService.select(identifier);

        return ResponseEntity.of(task.map(TaskController::toJson));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskIdentifierJson create(@RequestBody final TaskAttributeJson taskAttributeJson) {
        final var request = new TaskAttributes(taskAttributeJson.getId(), taskAttributeJson.getDetails(), taskAttributeJson.getStatus());

        final var identifier = taskService.insert(request);

        return new TaskIdentifierJson(identifier.getValue());
    }

    static TaskJson toJson(final Task task) {
        final var id = task.getIdentifier().getValue();
        return new TaskJson(id, task.getDetails(), task.getStatus());
    }

    @PatchMapping("/{id}")
    ResponseEntity<TaskJson> patch(@Uuid @PathVariable("id") final String id, @RequestBody final TaskAttributeJson taskAttributeJson) {
        final var identifier = new TaskIdentifier(id);
        final var request = new TaskAttributes(taskAttributeJson.getId(), taskAttributeJson.getDetails(), taskAttributeJson.getStatus());

        final var patchedTask = taskService.patch(identifier, request);
        return ResponseEntity.of(patchedTask.map(TaskController::toJson));
    }

}
