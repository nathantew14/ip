import util.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends CompletableTask {
    private LocalDateTime start;
    private LocalDateTime end;

    // Formatter for file storage
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Formatter for display
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");

    public Event(String title, String start, String end) {
        super(title);
        this.start = DateTimeParser.parse(start);
        this.end = DateTimeParser.parse(end);
    }

    public String getStart() {
        return start.format(DISPLAY_FORMAT).toLowerCase();
    }

    public String getEnd() {
        return end.format(DISPLAY_FORMAT).toLowerCase();
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
        return super.toFileFormat() + " | " + start.format(STORAGE_FORMAT) + " | " + end.format(STORAGE_FORMAT);
    }
}
