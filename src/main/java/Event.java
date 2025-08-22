
// Event.java
/**
 * Represents an event task with start and end date/time.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Constructs an Event with the given description, start and end date/time.
     *
     * @param description The description of the event.
     * @param from The start date/time.
     * @param to The end date/time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the string representation of the event.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
