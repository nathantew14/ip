
// Deadline.java
/**
 * Represents a deadline task with a due date/time.
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Constructs a Deadline with the given description and due date/time.
     *
     * @param description The description of the deadline.
     * @param by The due date/time.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the string representation of the deadline.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
