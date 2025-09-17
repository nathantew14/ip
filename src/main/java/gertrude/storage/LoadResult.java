package gertrude.storage;

import gertrude.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a result of loading tasks from storage.
 */
public class LoadResult {
    private final ReadTaskFileOutcome status;
    private final List<Task> tasks;
    private final List<String> badLines;

    public LoadResult(ReadTaskFileOutcome status, List<Task> tasks, List<String> badLines) {
        this.status = status;
        this.tasks = tasks;
        this.badLines = badLines;
    }

    public LoadResult(ReadTaskFileOutcome status, List<Task> tasks) {
        this.status = status;
        this.tasks = tasks;
        this.badLines = new ArrayList<>();
    }

    public ReadTaskFileOutcome getStatus() {
        return status;
    }

    /**
     * Returns the list of errors encountered during loading.
     *
     * @return the list of errors
     */
    public List<String> getBadLines() {
        return badLines;
    }

    /**
     * Returns the list of successfully loaded tasks.
     *
     * @return the list of tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }
}