import util.DateTimeParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends CompletableTask {
    private LocalDateTime deadline;

    // Formatter for file storage
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Formatter for display
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");

    public Deadline(String title, String deadline) {
        super(title);
        this.deadline = DateTimeParser.parse(deadline);
    }

    public String getDeadline() {
        return deadline.format(DISPLAY_FORMAT).toLowerCase();
    }

    @Override
    public String getTaskType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + getDeadline() + ")";
    }

    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + deadline.format(STORAGE_FORMAT);
    }
}
