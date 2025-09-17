package gertrude.task;

/**
 * Represents a to-do task in Gertrude.
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

    /**
     * Returns the string representation of the to-do task.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
