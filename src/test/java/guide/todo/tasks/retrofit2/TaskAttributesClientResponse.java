package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskAttributesClientResponse {
    private final String details;
    private final String status;

    @JsonCreator
    TaskAttributesClientResponse(
            @JsonProperty("details") final String details,
            @JsonProperty("status") final String status
    ) {
        this.details = details;
        this.status = status;
    }

    String getDetails() {
        return details;
    }

    String getStatus() {
        return status;
    }
}
