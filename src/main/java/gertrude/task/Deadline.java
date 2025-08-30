package gertrude.task;

import gertrude.util.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gertrude.exceptions.InvalidDateFormatException;

public class Deadline extends CompletableTask {
    private LocalDateTime deadline;

    public Deadline(String title, String deadline) throws InvalidDateFormatException {
        super(title);
        this.deadline = DateTimeParser.parse(deadline);
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public String getDeadlineAsString() {
        return deadline.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    public String getDeadlineAsString(String format) {
        return deadline.format(DateTimeFormatter.ofPattern(format));
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + getDeadlineAsString() + ")";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + deadline.format(DateTimeParser.STORAGE_FORMAT);
    }
}
