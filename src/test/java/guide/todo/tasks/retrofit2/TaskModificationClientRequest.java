package guide.todo.tasks.retrofit2;

class TaskModificationClientRequest {
    private final String details;
    private final String status;

    public TaskModificationClientRequest(
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
