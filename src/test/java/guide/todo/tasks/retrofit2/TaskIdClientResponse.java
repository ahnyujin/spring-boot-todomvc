package guide.todo.tasks.retrofit2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class TaskIdClientResponse {
    private final String id;

    @JsonCreator
    TaskIdClientResponse(
            @JsonProperty("id") final String id
    ) {
        this.id = id;
    }

    String getId() {
        return id;
    }
}
