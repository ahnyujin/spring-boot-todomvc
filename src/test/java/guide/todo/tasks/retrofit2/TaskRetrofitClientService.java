package guide.todo.tasks.retrofit2;

import guide.todo.tasks.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

class TaskRetrofitClientService implements TaskService {
    private final TaskRetrofitClient taskRetrofitClient;

    TaskRetrofitClientService(final TaskRetrofitClient taskRetrofitClient) {
        this.taskRetrofitClient = requireNonNull(taskRetrofitClient);
    }

    @Override
    public UUID insert(final TaskAttributesInsert taskAttributesInsert) {
        final var request = new TaskCreateClientRequest(
                taskAttributesInsert.getDetails()
        );

        final TaskIdClientResponse response =
                call(() -> taskRetrofitClient.create(request));

        return UUID.fromString(response.getId());
    }

    @Override
    public void delete(final UUID taskId) {
        final var request = toRequestTaskId(taskId);

        call(() -> taskRetrofitClient.delete(request));
    }

    @Override
    public TaskAttributes update(
            final UUID taskId,
            final TaskAttributes taskAttributes
    ) {
        final var requestTaskId = toRequestTaskId(taskId);
        final var requestTaskUpdateRequest = new TaskAttributesUpdateClientRequest(
                taskAttributes.getDetails(),
                toRequestTaskStatus(taskAttributes.getStatus())
        );

        final TaskAttributesClientResponse response =
                call(() -> taskRetrofitClient.update(
                        requestTaskId,
                        requestTaskUpdateRequest
                ));

        return toTaskAttributes(response);
    }

    @Override
    public TaskAttributes patch(
            final UUID taskId,
            final TaskAttributesPatch taskAttributesPatch
    ) {
        final var requestTaskId = toRequestTaskId(taskId);
        final String requestTaskStatus;
        if (taskAttributesPatch.getStatus() == null) {
            requestTaskStatus = null;
        } else {
            requestTaskStatus = toRequestTaskStatus(taskAttributesPatch.getStatus());
        }
        final var taskModificationRetrofitRequest = new TaskAttributesPatchClientRequest(
                taskAttributesPatch.getDetails(),
                requestTaskStatus
        );

        final TaskAttributesClientResponse response =
                call(() -> taskRetrofitClient.patch(
                        requestTaskId,
                        taskModificationRetrofitRequest
                ));

        return toTaskAttributes(response);
    }

    @Override
    public Optional<TaskAttributes> select(UUID taskId) {
        final var requestTaskId = toRequestTaskId(taskId);

        final TaskAttributesClientResponse response;
        try {
            response = call(() -> taskRetrofitClient.get(requestTaskId));
        } catch (NoEntityException e) {
            return Optional.empty();
        }
        return Optional.of(toTaskAttributes(response));
    }

    @Override
    public List<Task> selectAll() {
        final var response = call(taskRetrofitClient::getAll);

        return response.stream()
                .map(TaskRetrofitClientService::toTask)
                .collect(Collectors.toUnmodifiableList());
    }

    static <T> T call(Supplier<Call<T>> supplier) {
        final var call = supplier.get();
        final Response<T> response;
        try {
            response = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (response.isSuccessful()) {
            return response.body();
        }

        if (response.code() == 404) {
            throw new NoEntityException(response.message());
        }
        throw new RuntimeException(response.message());
    }

    static String toRequestTaskId(final UUID taskId) {
        return taskId.toString();
    }

    static UUID toRequestTaskId(final String taskIdString) {
        return UUID.fromString(taskIdString);
    }


    static String toRequestTaskStatus(final TaskStatus taskStatus) {
        return taskStatus.name().toLowerCase(Locale.ENGLISH);
    }

    static TaskStatus toTaskStatus(final String taskStatusString) {
        return TaskStatus.valueOf(taskStatusString.toUpperCase(Locale.ENGLISH));
    }

    static TaskAttributes toTaskAttributes(final TaskAttributesClientResponse taskAttributesClientResponse) {
        return new TaskAttributes(
                taskAttributesClientResponse.getDetails(),
                toTaskStatus(taskAttributesClientResponse.getStatus())
        );
    }

    static Task toTask(final TaskClientResponse taskClientResponse) {
        return new Task(
                toRequestTaskId(taskClientResponse.getId()),
                taskClientResponse.getDetails(),
                toTaskStatus(taskClientResponse.getStatus())
        );
    }
}
