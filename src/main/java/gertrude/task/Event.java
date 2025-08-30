package gertrude.task;

import gertrude.util.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gertrude.exceptions.InvalidDateFormatException;

public class Event extends CompletableTask {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String title, String start, String end) throws InvalidDateFormatException {
        super(title);
        this.start = DateTimeParser.parse(start);
        this.end = DateTimeParser.parse(end);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public String getStartAsString() {
        return start.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    public String getStartAsString(String format) {
        return start.format(DateTimeFormatter.ofPattern(format));
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public String getEndAsString() {
        return end.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    public String getEndAsString(String format) {
        return end.format(DateTimeFormatter.ofPattern(format));
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + getStartAsString() + " to: " + getEndAsString() + ")";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + start.format(DateTimeParser.STORAGE_FORMAT) + " | "
                + end.format(DateTimeParser.STORAGE_FORMAT);
    }
}
