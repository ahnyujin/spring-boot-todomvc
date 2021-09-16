package guide.todo.tasks;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
class TaskController {
    private final TaskService taskService;

    TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    List<Task> retrieveAll() {
        return taskService.selectAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<TaskAttributes> retrieveById(@PathVariable("id") final UUID id) {
        return ResponseEntity.of(taskService.select(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Map<String, UUID> create(@RequestBody final TaskAttributes attributes) {
        attributes.setStatus("active");
        return Map.of("id", taskService.insert(attributes));
    }

    @PutMapping("/{id}")
    TaskAttributes update(
            @PathVariable("id") final UUID id,
            @RequestBody final TaskAttributes attributes
    ) {
        return taskService.update(id, attributes);
    }

    @PatchMapping("/{id}")
    TaskAttributes patch(
            @PathVariable("id") final UUID id,
            @RequestBody final TaskAttributes attributes
    ) {
        return taskService.patch(id, attributes);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") final UUID id) {
        taskService.delete(id);
    }
}
