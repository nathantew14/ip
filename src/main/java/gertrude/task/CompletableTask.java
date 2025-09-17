package gertrude.task;

/**
 * Represents a completable task in Gertrude.
 */
public class CompletableTask extends Task {
    private boolean isCompleted = false;

    /**
     * Constructs a CompletableTask with the specified title.
     *
     * @param title The title of the task.
     */
    public CompletableTask(String title) {
        super(title);
    }

    /**
     * Returns the task type of the completable task.
     *
     * @return The task type, "CompletableTask".
     */
    @Override
    public String getTaskType() {
        return "CompletableTask";
    }

    /**
     * Toggles the completion status of the task.
     */
    public void toggleCompleted() {
        isCompleted = !isCompleted;
    }

    /**
     * Marks the task as completed.
     */
    public void setCompleted() {
        isCompleted = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void setNotCompleted() {
        isCompleted = false;
    }

    /**
     * Checks if the task is completed.
     *
     * @return True if the task is completed, false otherwise.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Formats the task for display with its index.
     *
     * @param index The index of the task.
     * @return A formatted string representing the task.
     */
    public String format(int index) {
        return (index + 1) + ". " + toString();
    }

    /**
     * Returns the task in a file-friendly format.
     *
     * @return A string representation of the task for file storage.
     */
    public String toFileFormat() {
        return getTaskType() + " | " + (isCompleted() ? "1" : "0") + " | " + getTitle();
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string describing the task, including its completion status.
     */
    @Override
    public String toString() {
        return String.format("[%s] %s %s", getTaskType(), isCompleted ? "[X]" : "[ ]", title);
    }

    /**
     * Marks the task as completed and logs the completion.
     */
    @Override
    public void markAsCompleted() {
        setCompleted();
        // Add logging logic here if needed
    }
}
