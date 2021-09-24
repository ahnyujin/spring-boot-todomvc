package guide.springboot.sample.controller;

import guide.springboot.sample.tasks.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
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
                .map(TaskController::toTaskResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    @GetMapping("/{id}")
    Optional<TaskAttributesResponse> retrieveById(@PathVariable("id") final String taskIdString) {
        final var taskId = toTaskId(taskIdString);

        final var task = taskService.select(taskId);

        return task.map(TaskController::toTaskAttributesResponse);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TaskIdResponse create(@RequestBody final TaskCreateRequest taskCreateRequest) {
        final var taskAttributesInsert = new TaskAttributesInsert(taskCreateRequest.getDetails());

        final var createdTaskId = taskService.insert(taskAttributesInsert);

        return toTaskIdResponse(createdTaskId);
    }

    @PutMapping("/{id}")
    TaskAttributesResponse update(@PathVariable("id") final String taskIdString, @RequestBody final TaskUpdateRequest taskUpdateRequest) {
        final var taskId = toTaskId(taskIdString);
        final var request = new TaskAttributes( taskUpdateRequest.getDetails(), toTaskStatus(taskUpdateRequest.getStatus()));

        final var updatedTask = taskService.update(taskId, request);
        return toTaskAttributesResponse(updatedTask);
    }

    @PatchMapping("/{id}")
    TaskAttributesResponse patch(@PathVariable("id") final String taskIdString, @RequestBody final TaskPatchRequest taskPatchRequest) {
        final var taskId = toTaskId(taskIdString);
        final var taskAttributesPatch = new TaskAttributesPatch(taskPatchRequest.getDetails(), toTaskStatus(taskPatchRequest.getStatus()));

        final var patchedTask = taskService.patch(taskId, taskAttributesPatch);
        return toTaskAttributesResponse(patchedTask);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") final String taskIdString){
        final var taskId = toTaskId(taskIdString);
        taskService.delete(taskId);
    }

    static UUID toTaskId(String taskIdString) {
        return UUID.fromString(taskIdString);
    }

    static TaskResponse toTaskResponse(final Task task) {
        return new TaskResponse(task.getId().toString(), task.getDetails(), toTaskStatusString(task.getStatus()));
    }

    static TaskIdResponse toTaskIdResponse(final UUID id) {
        return new TaskIdResponse(id.toString());
    }

    static TaskAttributesResponse toTaskAttributesResponse(final TaskAttributes attributes) {
        return new TaskAttributesResponse(
                attributes.getDetails(),
                toTaskStatusString(attributes.getStatus())
        );
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
