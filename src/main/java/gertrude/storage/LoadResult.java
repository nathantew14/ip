package gertrude.storage;

import java.util.ArrayList;
import java.util.List;

import gertrude.task.Task;

/**
 * Represents a result of loading tasks from storage.
 */
public class LoadResult {
    private final ReadTaskFileOutcome status;
    private final List<Task> tasks;
    private final List<String> badLines;

    /**
     * Constructs a LoadResult with the specified status, tasks, and bad lines.
     *
     * @param status   the outcome of reading the task file
     * @param tasks    the list of successfully loaded tasks
     * @param badLines the list of lines that could not be parsed
     */
    public LoadResult(ReadTaskFileOutcome status, List<Task> tasks, List<String> badLines) {
        this.status = status;
        this.tasks = tasks;
        this.badLines = badLines;
    }

    /**
     * Constructs a LoadResult with the specified status and tasks.
     * Initializes the bad lines list to an empty list.
     *
     * @param status the outcome of reading the task file
     * @param tasks  the list of successfully loaded tasks
     */
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
