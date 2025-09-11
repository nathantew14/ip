package gertrude.task;

import gertrude.exceptions.SaveFileBadLineException;

/**
 * Represents a generic task with a title and completion status.
 */
public abstract class Task {
    protected String title;

    /**
     * Constructs a Task with the specified title.
     *
     * @param title The title of the task.
     */
    public Task(String title) {
        this.title = title;
    }

    /**
     * Returns the title of the task.
     *
     * @return The title of the task.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the task type.
     *
     * @return The task type.
     */
    public String getTaskType() {
        return "Task";
    }

    /**
     * Returns the title prefix for the task.
     *
     * @return The title prefix.
     */
    public String getTitlePrefix() {
        return String.format("[%s] ", getTaskType());
    }

    /**
     * Formats the task for display with its index.
     *
     * @param index The index of the task.
     * @return A formatted string representing the task.
     */
    public abstract String format(int index);

    /**
     * Converts the task to a text format.
     *
     * @return A string representation of the task for file storage.
     */
    public abstract String toFileFormat();

    /**
     * Parses a task from a file-friendly format.
     *
     * @param line The string representation of the task from the file.
     * @return The parsed Task object.
     */
    public static Task fromFileFormat(String line) throws SaveFileBadLineException {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isCompleted = parts[1].equals("1");
            String title = parts[2];

            switch (type) {
            case "T":
                Todo todo = new Todo(title);
                if (isCompleted) {
                    todo.setCompleted();
                }
                return todo;
            case "D":
                String deadline = parts[3];
                Deadline deadlineTask = new Deadline(title, deadline);
                if (isCompleted) {
                    deadlineTask.setCompleted();
                }
                return deadlineTask;
            case "E":
                String start = parts[3];
                String end = parts[4];
                Event event = new Event(title, start, end);
                if (isCompleted) {
                    event.setCompleted();
                }
                return event;
            default:
                throw new SaveFileBadLineException(null);
            }
        } catch (Exception e) {
            throw new SaveFileBadLineException(null);
        }
    }

    /**
     * Returns a string representation of the task.
     *
     * @return A string describing the task.
     */
    @Override
    public String toString() {
        return getTitlePrefix() + title;
    }
}
