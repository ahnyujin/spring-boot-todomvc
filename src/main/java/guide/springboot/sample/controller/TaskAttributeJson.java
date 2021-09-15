package guide.springboot.sample.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskAttributeJson {
    private final String id;
    private final String details;
    private final String status;

    @JsonCreator
    public TaskAttributeJson(@JsonProperty("id") String id, @JsonProperty("details") String details,@JsonProperty("status") String status) {
        this.id = id;
        this.details = details;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDetails() {
        return details;
    }

    public String getStatus() {
        return status;
    }


}
