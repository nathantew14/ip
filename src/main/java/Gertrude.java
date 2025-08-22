import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Gertrude {
    private static final String ADD_TODO_PREFIX = "task:";
    private static final String LIST_TODOS_COMMAND = "list";
    private static final String COMPLETE_TODO_PREFIX = "mark:";
    private static final String UNCOMPLETE_TODO_PREFIX = "unmark:";
    private static final String BY_TAG = "/by";
    private static final String START_TAG = "/start";
    private static final String END_TAG = "/end";
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("\nWelcome, dear! I'm Gertrude, your friendly AI chatbot.\n"
                + "-------------------------------------------------------------------------\n"
                + "If you need help, advice, or just a little chat, I'm always here for you.\n"
                + "Now, what can I do for you today, sweetheart?\n"
                + "-------------------------------------------------------------------------");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nYou: ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) break;
            System.out.println("Gertrude: " + getResponse(input));
        }

        System.out.println("Gertrude: Goodbye, dear! Take care and come back anytime you need me.");
        scanner.close();
    }

    private static String getResponse(String input) {
        if (input.toLowerCase().startsWith(ADD_TODO_PREFIX)) {
            String content = input.substring(ADD_TODO_PREFIX.length()).trim();
            if (!content.isEmpty()) {
                int byIndex = content.indexOf(BY_TAG);
                int startIndex = content.indexOf(START_TAG);
                int endIndex = content.indexOf(END_TAG);

                // Check for invalid combinations
                if ((byIndex != -1 && (startIndex != -1 || endIndex != -1)) ||
                    (startIndex != -1 && endIndex == -1) ||
                    (endIndex != -1 && startIndex == -1)) {
                    return "Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.";
                }

                if (byIndex != -1) {
                    String title = content.substring(0, byIndex).trim();
                    String deadline = content.substring(byIndex + BY_TAG.length()).trim();
                    if (!title.isEmpty() && !deadline.isEmpty()) {
                        Deadline dl = new Deadline(title, deadline);
                        tasks.add(dl);
                        return "Added new deadline: " + dl.getTitle() + " (by: " + dl.getDeadline() + ")";
                    } else {
                        return "Please provide both a title and a deadline.";
                    }
                } else if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
                    String title = content.substring(0, startIndex).trim();
                    String start = content.substring(startIndex + START_TAG.length(), endIndex).trim();
                    String end = content.substring(endIndex + END_TAG.length()).trim();
                    if (!title.isEmpty() && !start.isEmpty() && !end.isEmpty()) {
                        Event event = new Event(title, start, end);
                        tasks.add(event);
                        return "Added new event: " + event.getTitle() + " (from: " + event.getStart() + " to: " + event.getEnd() + ")";
                    } else {
                        return "Please provide a title, start, and end for the event.";
                    }
                } else if (startIndex == -1 && endIndex == -1) {
                    Todo todo = new Todo(content);
                    tasks.add(todo);
                    return "Added new todo: " + todo.getTitle();
                } else {
                    return "Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.";
                }
            } else {
                return "Please provide a title for the todo.";
            }
        }

        if (input.equalsIgnoreCase(LIST_TODOS_COMMAND)) {
            if (tasks.isEmpty()) {
                return "No tasks yet, dear!";
            }
            StringBuilder sb = new StringBuilder("Here are your tasks:\n");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append((tasks.get(i)).format(i) + "\n");
            }
            return sb.toString().trim();
        }

        if (input.toLowerCase().startsWith(COMPLETE_TODO_PREFIX)) {
            String idxStr = input.substring(COMPLETE_TODO_PREFIX.length()).trim();
            try {
                int idx = Integer.parseInt(idxStr) - 1;
                if (idx >= 0 && idx < tasks.size()) {
                    Task t = tasks.get(idx);
                    if (t instanceof CompletableTask) {
                        ((CompletableTask)t).setCompleted();
                        return "Marked task #" + (idx + 1) + " as completed.";
                    } else {
                        return "Task #" + (idx + 1) + " cannot be marked as completed.";
                    }
                } else {
                    return "Invalid task index, dear!";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid task index to complete.";
            }
        }

        if (input.toLowerCase().startsWith(UNCOMPLETE_TODO_PREFIX)) {
            String idxStr = input.substring(UNCOMPLETE_TODO_PREFIX.length()).trim();
            try {
                int idx = Integer.parseInt(idxStr) - 1;
                if (idx >= 0 && idx < tasks.size()) {
                    Task t = tasks.get(idx);
                    if (t instanceof CompletableTask) {
                        CompletableTask ct = (CompletableTask)t;
                        if (ct.isCompleted()) {
                            ct.setNotCompleted();
                            return "Marked task #" + (idx + 1) + " as not completed.";
                        } else {
                            return "Task #" + (idx + 1) + " is already not completed.";
                        }
                    } else {
                        return "Task #" + (idx + 1) + " cannot be marked as not completed.";
                    }
                } else {
                    return "Invalid task index, dear!";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid task index to uncomplete.";
            }
        }

        return input;
    }
}
