package gertrude.task;

import gertrude.util.DateTimeParser;
import java.time.LocalDateTime;

import gertrude.exceptions.InvalidDateFormatException;

public class Event extends CompletableTask {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String title, String start, String end) throws InvalidDateFormatException {
        super(title);
        this.start = DateTimeParser.parse(start);
        this.end = DateTimeParser.parse(end);
    }

    public String getStart() {
        return start.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    public String getEnd() {
        return end.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
    }

    @Override
    public String getTaskType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + getStart() + " to: " + getEnd() + ")";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + start.format(DateTimeParser.STORAGE_FORMAT) + " | " + end.format(DateTimeParser.STORAGE_FORMAT);
    }
}
