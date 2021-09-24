package guide.springboot.sample.controller;

import guide.springboot.sample.lang.validation.Uuid;
import guide.springboot.sample.tasks.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    List<TaskResponse> retrieveAll() {
        final var tasks = taskService.selectAll();

        return tasks.stream()
                .map(TaskController::toJson)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskResponse> retrieveById(@Uuid @PathVariable("id") final String id) {
        final var identifier = new TaskIdentifier(id);

        final var task = taskService.select(identifier);

        return ResponseEntity.of(task.map(TaskController::toJson));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskIdResponse create(@RequestBody final TaskCreateRequest taskCreateRequest) {
        final var taskAttributesInsert = new TaskAttributesInsert(taskCreateRequest.getDetails());

        final var createdTaskIdentifier = taskService.insert(taskAttributesInsert);

        return new TaskIdResponse(createdTaskIdentifier.getValue());
    }

    @PutMapping("/{id}")
    ResponseEntity update(@Uuid @PathVariable("id") final String id, @RequestBody final TaskUpdateRequest taskUpdateRequest) {
        final var identifier = new TaskIdentifier(id);
        final var request = new TaskAttributes( taskUpdateRequest.getDetails(), toTaskStatus(taskUpdateRequest.getStatus()));

        final var updatedTask = taskService.update(identifier, request);
        return ResponseEntity.ok().body(toJson(updatedTask));
    }

    @PatchMapping("/{id}")
    ResponseEntity<TaskResponse> patch(@Uuid @PathVariable("id") final String id, @RequestBody final TaskPatchRequest taskPatchRequest) {
        final var identifier = new TaskIdentifier(id);
        final var request = new TaskAttributesPatch(taskPatchRequest.getDetails(), toTaskStatus(taskPatchRequest.getStatus()));

        final var patchedTask = taskService.patch(identifier, request);
        return ResponseEntity.of(patchedTask.map(TaskController::toJson));
    }

    @DeleteMapping("/{id}")
    void delete(@Uuid @PathVariable("id") final String id){
        final var identifier = new TaskIdentifier(id);
        taskService.delete(identifier);
    }

    static TaskResponse toJson(final Task task) {
        final var id = task.getIdentifier().getValue();
        return new TaskResponse(id, task.getDetails(), toTaskStatusString(task.getStatus()));
    }

    static String toTaskStatusString(final TaskStatus taskStatus) {
        return taskStatus.name().toLowerCase(Locale.ENGLISH);
    }

    static TaskStatus toTaskStatus(final String taskStatusString) {
        try {
            return TaskStatus.valueOf(taskStatusString.toUpperCase(Locale.ENGLISH));
        } catch (Exception e){
            return null;
        }
    }
}
