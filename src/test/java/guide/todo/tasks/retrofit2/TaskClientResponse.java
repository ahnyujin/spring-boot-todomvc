package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskClientResponse {
    private final String id;
    private final String details;
    private final String status;

    @JsonCreator
    TaskClientResponse(
            @JsonProperty("id") String id,
            @JsonProperty("details") String details,
            @JsonProperty("status") String status
    ) {
        this.id = id;
        this.details = details;
        this.status = status;
    }

    String getId() {
        return id;
    }

    String getDetails() {
        return details;
    }

    String getStatus() {
        return status;
    }
}
