package guide.todo.tasks.retrofit2;

import static java.util.Objects.requireNonNull;

class TaskAttributesUpdateClientRequest {
    private final String details;
    private final String status;

    TaskAttributesUpdateClientRequest(
            final String details,
            final String status
    ) {
        this.details = requireNonNull(details);
        this.status = requireNonNull(status);
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }
}
