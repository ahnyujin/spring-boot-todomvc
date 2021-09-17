package guide.todo.tasks.retrofit2;

class TaskAttributesPatchClientRequest {
    private final String details;
    private final String status;

    TaskAttributesPatchClientRequest(
            final String details,
            final String status
    ) {
        this.details = details;
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }
}
