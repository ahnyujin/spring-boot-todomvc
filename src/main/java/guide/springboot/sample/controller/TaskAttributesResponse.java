package guide.springboot.sample.controller;

import static java.util.Objects.requireNonNull;

public class TaskAttributesResponse {
    private final String details;
    private final String status;

    TaskAttributesResponse(final String details, final String status) {
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
