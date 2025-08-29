import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends CompletableTask {
    private LocalDateTime deadline;

    // Formatter for file storage
    private static final DateTimeFormatter STORAGE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    // Formatter for display
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("dd MMM yyyy, h:mma");

    // Flexible input patterns
    private static final String[] DATE_TIME_PATTERNS = {
        "d/M/yyyy HHmm", "d/M/yyyy H:mm", "d/M/yyyy HH:mm", "d/M/yyyy",
        "yyyy-MM-dd HHmm", "yyyy-MM-dd HH:mm", "yyyy-MM-dd"
    };

    public Deadline(String title, String deadline) {
        super(title);
        this.deadline = parseFlexibleDateTime(deadline);
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

    private static LocalDateTime parseFlexibleDateTime(String input) {
        for (String pattern : DATE_TIME_PATTERNS) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
                if (pattern.contains("H")) {
                    return LocalDateTime.parse(input, formatter);
                } else {
                    return LocalDateTime.parse(input + "T00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                }
            } catch (DateTimeParseException ignored) {}
        }
        throw new IllegalArgumentException("Invalid date format. Please use formats like '2/12/2019 1800' or '2019-12-02'.");
    }
}
