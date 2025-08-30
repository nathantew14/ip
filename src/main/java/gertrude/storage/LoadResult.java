package gertrude.storage;

import gertrude.task.Task;
import java.util.List;

public class LoadResult {
    private final ReadTaskFileOutcome status;
    private final List<Task> tasks;

    public LoadResult(ReadTaskFileOutcome status, List<Task> tasks) {
        this.status = status;
        this.tasks = tasks;
    }

    public ReadTaskFileOutcome getStatus() {
        return status;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}