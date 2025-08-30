package gertrude.task;

/**
 * Represents a simple todo task.
 */
public class Todo extends CompletableTask {

    /**
     * Constructs a Todo with the specified title.
     *
     * @param title The title of the todo task.
     */
    public Todo(String title) {
        super(title);
    }

    /**
     * Returns the task type of the todo.
     *
     * @return The task type, "T".
     */
    @Override
    public String getTaskType() {
        return "T";
    }
}
