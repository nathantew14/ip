import util.DateTimeParser;
import java.time.LocalDateTime;

public class Deadline extends CompletableTask {
    private LocalDateTime deadline;

    public Deadline(String title, String deadline) throws InvalidDateFormatException {
        super(title);
        this.deadline = DateTimeParser.parse(deadline);
    }

    public String getDeadline() {
        return deadline.format(DateTimeParser.DISPLAY_FORMAT).toLowerCase();
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
        return super.toFileFormat() + " | " + deadline.format(DateTimeParser.STORAGE_FORMAT);
    }
}
