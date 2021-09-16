package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskCreateClientRequest {
    private final String details;

    @JsonCreator
    public TaskCreateClientRequest(
            @JsonProperty("details") final String details
    ) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
