package gertrude.task;

import gertrude.util.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gertrude.exceptions.InvalidDateFormatException;

/**
 * Represents a deadline task with a specific due date and time.
 */
public class Deadline extends CompletableTask {
    private LocalDateTime deadline;

    /**
     * Constructs a Deadline with the specified title and deadline.
     *
     * @param title    The title of the deadline.
     * @param deadline The deadline in a parsable format.
     * @throws InvalidDateFormatException If the deadline is invalid.
     */
    public Deadline(String title, String deadline) throws InvalidDateFormatException {
        super(title);
        this.deadline = DateTimeParser.parse(deadline);
    }

    /**
     * Returns the deadline as a LocalDateTime object.
     *
     * @return The deadline.
     */
    public LocalDateTime getDeadline() {
        return deadline;
    }

    /**
     * Returns the deadline as a formatted string.
     *
     * @return The deadline in the default display format.
     */
    public String getDeadlineAsString() {
        return deadline.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    /**
     * Returns the deadline as a formatted string using a custom format.
     *
     * @param format The custom format to use.
     * @return The deadline in the specified format.
     */
    public String getDeadlineAsString(String format) {
        return deadline.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Returns the task type of the deadline.
     *
     * @return The task type, "D".
     */
    @Override
    public String getTaskType() {
        return "D";
    }

    /**
     * Returns a string representation of the deadline.
     *
     * @return A string describing the deadline, including its due date and time.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + getDeadlineAsString() + ")";
    }

    /**
     * Returns the deadline in a file-friendly format.
     *
     * @return A string representation of the deadline for file storage.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + deadline.format(DateTimeParser.STORAGE_FORMAT);
    }
}
