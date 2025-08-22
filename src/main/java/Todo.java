
// Todo.java
/**
 * Represents a todo task without any date/time.
 */
public class Todo extends Task {
    /**
     * Constructs a Todo with the given description.
     *
     * @param description The description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
