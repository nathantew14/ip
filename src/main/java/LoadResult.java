import java.util.List;

public class LoadResult {
    public enum Status {
        SUCCESS,
        NO_FILE_FOUND,
        ERROR_READING_FILE
    }

    private final Status status;
    private final List<Task> tasks;

    public LoadResult(Status status, List<Task> tasks) {
        this.status = status;
        this.tasks = tasks;
    }

    public Status getStatus() {
        return status;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}