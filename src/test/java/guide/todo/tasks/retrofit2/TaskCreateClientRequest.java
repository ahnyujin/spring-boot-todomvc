package guide.todo.tasks.retrofit2;

import static java.util.Objects.requireNonNull;

class TaskCreateClientRequest {
    private final String details;

    TaskCreateClientRequest(
            final String details
    ) {
        this.details = requireNonNull(details);
    }

    public String getDetails() {
        return details;
    }
}
