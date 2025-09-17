package gertrude.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gertrude.exceptions.InvalidDateFormatException;
import gertrude.util.DateTimeParser;

/**
 * Represents an event task with a start and end time.
 */
public class Event extends CompletableTask {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Constructs an Event with the specified title, start time, and end time.
     *
     * @param title The title of the event.
     * @param start The start time of the event in a parsable format.
     * @param end   The end time of the event in a parsable format.
     * @throws InvalidDateFormatException If the start or end time is invalid.
     */
    public Event(String title, String start, String end) throws InvalidDateFormatException {
        super(title);
        this.start = DateTimeParser.parse(start);
        this.end = DateTimeParser.parse(end);
    }

    /**
     * Returns the start time of the event as a LocalDateTime object.
     *
     * @return The start time of the event.
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * Returns the start time of the event as a formatted string.
     *
     * @return The start time of the event in the default display format.
     */
    public String getStartAsString() {
        return start.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    /**
     * Returns the start time of the event as a formatted string using a custom
     * format.
     *
     * @param format The custom format to use.
     * @return The start time of the event in the specified format.
     */
    public String getStartAsString(String format) {
        return start.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Returns the end time of the event as a LocalDateTime object.
     *
     * @return The end time of the event.
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Returns the end time of the event as a formatted string.
     *
     * @return The end time of the event in the default display format.
     */
    public String getEndAsString() {
        return end.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    /**
     * Returns the end time of the event as a formatted string using a custom
     * format.
     *
     * @param format The custom format to use.
     * @return The end time of the event in the specified format.
     */
    public String getEndAsString(String format) {
        return end.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * Returns the task type of the event.
     *
     * @return The task type, "E".
     */
    @Override
    public String getTaskType() {
        return "E";
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string describing the event, including its start and end times.
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + getStartAsString() + " to: " + getEndAsString() + ")";
    }

    /**
     * Returns the event in a file-friendly format.
     *
     * @return A string representation of the event for file storage.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + start.format(DateTimeParser.STORAGE_FORMAT) + " | "
                + end.format(DateTimeParser.STORAGE_FORMAT);
    }
}
