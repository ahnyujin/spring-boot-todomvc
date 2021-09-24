package guide.springboot.sample.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

public class TaskCreateRequest {
    private final String details;

    @JsonCreator
    public TaskCreateRequest(@JsonProperty(value="details", required = true) final String details) {
        this.details = requireNonNull(details);
    }

    public String getDetails() {
        return details;
    }
}
