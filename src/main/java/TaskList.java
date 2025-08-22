
// TaskList.java
import java.util.ArrayList;

/**
 * Manages the list of tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Constructs a TaskList with the given list of tasks.
     *
     * @param tasks The initial list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task based on the input string.
     *
     * @param input The input string containing task details.
     * @return The response message.
     * @throws GertrudeException If there's an error in the input.
     */
    public String addTask(String input) throws GertrudeException {
        if (input.startsWith("todo")) {
            return addTodo(input);
        } else if (input.startsWith("deadline")) {
            return addDeadline(input);
        } else if (input.startsWith("event")) {
            return addEvent(input);
        } else {
            // Default to todo if no specific type is mentioned
            if (input.trim().isEmpty()) {
                throw new GertrudeException("Please provide a title for the todo.");
            }
            Todo todo = new Todo(input);
            tasks.add(todo);
            return "Gertrude: Added new todo: " + input;
        }
    }

    private String addTodo(String input) throws GertrudeException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new GertrudeException("Please provide a title for the todo.");
        }
        Todo todo = new Todo(description);
        tasks.add(todo);
        return "Gertrude: Added new todo: " + description;
    }

    private String addDeadline(String input) throws GertrudeException {
        String withoutKeyword = input.substring(8).trim();
        if (!withoutKeyword.contains("/by")) {
            throw new GertrudeException("Please provide both a title and a deadline.");
        }
        
        String[] parts = withoutKeyword.split("/by", 2);
        String description = parts[0].trim();
        String by = parts[1].trim();
        
        if (description.isEmpty() || by.isEmpty()) {
            throw new GertrudeException("Please provide both a title and a deadline.");
        }
        
        // Check for invalid tags
        if (by.contains("/start") || by.contains("/end")) {
            throw new GertrudeException("Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
        }
        
        Deadline deadline = new Deadline(description, by);
        tasks.add(deadline);
        return "Gertrude: Added new deadline: " + description + " (by: " + by + ")";
    }

    private String addEvent(String input) throws GertrudeException {
        String withoutKeyword = input.substring(5).trim();
        if (!withoutKeyword.contains("/start") || !withoutKeyword.contains("/end")) {
            throw new GertrudeException("Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
        }
        
        String[] startParts = withoutKeyword.split("/start", 2);
        String description = startParts[0].trim();
        
        String[] endParts = startParts[1].split("/end", 2);
        String from = endParts[0].trim();
        String to = endParts[1].trim();
        
        if (description.isEmpty() || from.isEmpty() || to.isEmpty()) {
            throw new GertrudeException("Please provide a title, start time, and end time for the event.");
        }
        
        // Check for invalid tags
        if (description.contains("/by") || from.contains("/by") || to.contains("/by")) {
            throw new GertrudeException("Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
        }
        
        Event event = new Event(description, from, to);
        tasks.add(event);
        return "Gertrude: Added new event: " + description + " (from: " + from + " to: " + to + ")";
    }

    /**
     * Lists all tasks.
     *
     * @return The list of tasks as a string.
     */
    public String listTasks() {
        if (tasks.isEmpty()) {
            return "Gertrude: No tasks yet, dear!";
        }
        
        StringBuilder sb = new StringBuilder("Gertrude: Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i).toString() + "\n");
        }
        return sb.toString().trim();
    }

    /**
     * Marks a task as done.
     *
     * @param input The input string containing the task index.
     * @return The response message.
     * @throws GertrudeException If there's an error in the input.
     */
    public String markTask(String input) throws GertrudeException {
        String indexStr = input.substring(5).trim();
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new GertrudeException("Invalid task index, dear!");
            }
            tasks.get(index).markAsDone();
            return "Gertrude: Marked task #" + (index + 1) + " as completed.";
        } catch (NumberFormatException e) {
            throw new GertrudeException("Please provide a valid task index to mark.");
        }
    }

    /**
     * Marks a task as not done.
     *
     * @param input The input string containing the task index.
     * @return The response message.
     * @throws GertrudeException If there's an error in the input.
     */
    public String unmarkTask(String input) throws GertrudeException {
        String indexStr = input.substring(7).trim();
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new GertrudeException("Invalid task index, dear!");
            }
            tasks.get(index).markAsNotDone();
            return "Gertrude: Marked task #" + (index + 1) + " as not completed.";
        } catch (NumberFormatException e) {
            throw new GertrudeException("Please provide a valid task index to unmark.");
        }
    }

    /**
     * Removes a task.
     *
     * @param input The input string containing the task index.
     * @return The response message.
     * @throws GertrudeException If there's an error in the input.
     */
    public String removeTask(String input) throws GertrudeException {
        String indexStr = input.substring(7).trim();
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= tasks.size()) {
                throw new GertrudeException("Invalid task index, dear!");
            }
            Task removedTask = tasks.remove(index);
            return "Gertrude: Removed task #" + (index + 1) + ": " + removedTask.description;
        } catch (NumberFormatException e) {
            throw new GertrudeException("Please provide a valid task index to remove.");
        }
    }
}
