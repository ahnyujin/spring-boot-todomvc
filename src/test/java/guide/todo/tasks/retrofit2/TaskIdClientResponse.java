package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskIdClientResponse {
    private final String id;

    @JsonCreator
    public TaskIdClientResponse(
            @JsonProperty("id") final String id
    ) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
