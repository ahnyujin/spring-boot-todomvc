package guide.springboot.sample.tasks;

import java.util.Objects;

public class TaskIdentifier {
    private final String value;

    public TaskIdentifier(final String value) {
        this.value = value;}
    public String getValue() {
        return value;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskIdentifier that = (TaskIdentifier) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


}
