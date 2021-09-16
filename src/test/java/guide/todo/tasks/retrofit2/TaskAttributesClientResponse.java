package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskAttributesClientResponse {
    private final String details;
    private final String status;

    @JsonCreator
    public TaskAttributesClientResponse(
            @JsonProperty("details") final String details,
            @JsonProperty("status") final String status
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
