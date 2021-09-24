package guide.springboot.sample.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

public class TaskUpdateRequest {
    private final String details;
    private final String status;

    @JsonCreator
    public TaskUpdateRequest(@JsonProperty("details") final String details, @JsonProperty("status") final String status) {
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
