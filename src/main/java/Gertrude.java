import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Gertrude {
    private static final String ADD_TODO_PREFIX = "todo:";
    private static final String LIST_TODOS_COMMAND = "list";
    private static final String COMPLETE_TODO_PREFIX = "mark:";
    private static final String UNCOMPLETE_TODO_PREFIX = "unmark:";
    private static List<Todo> todos = new ArrayList<>();

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
            String title = input.substring(ADD_TODO_PREFIX.length()).trim();
            if (!title.isEmpty()) {
                Todo todo = new Todo(title);
                todos.add(todo);
                return "Added new todo: " + todo.getTitle();
            } else {
                return "Please provide a title for the todo.";
            }
        }

        if (input.equalsIgnoreCase(LIST_TODOS_COMMAND)) {
            if (todos.isEmpty()) {
                return "No todos yet, dear!";
            }
            StringBuilder sb = new StringBuilder("Here are your todos:\n");
            for (int i = 0; i < todos.size(); i++) {
                sb.append(todos.get(i).format(i) + "\n");
            }
            return sb.toString().trim();
        }

        if (input.toLowerCase().startsWith(COMPLETE_TODO_PREFIX)) {
            String idxStr = input.substring(COMPLETE_TODO_PREFIX.length()).trim();
            try {
                int idx = Integer.parseInt(idxStr) - 1;
                if (idx >= 0 && idx < todos.size()) {
                    todos.get(idx).setCompleted();
                    return "Marked todo #" + (idx + 1) + " as completed.";
                } else {
                    return "Invalid todo index, dear!";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid todo index to complete.";
            }
        }

        if (input.toLowerCase().startsWith(UNCOMPLETE_TODO_PREFIX)) {
            String idxStr = input.substring(UNCOMPLETE_TODO_PREFIX.length()).trim();
            try {
                int idx = Integer.parseInt(idxStr) - 1;
                if (idx >= 0 && idx < todos.size()) {
                    Todo todo = todos.get(idx);
                    if (todo.isCompleted()) {
                        todo.setNotCompleted();
                        return "Marked todo #" + (idx + 1) + " as not completed.";
                    } else {
                        return "Todo #" + (idx + 1) + " is already not completed.";
                    }
                } else {
                    return "Invalid todo index, dear!";
                }
            } catch (NumberFormatException e) {
                return "Please provide a valid todo index to uncomplete.";
            }
        }

        return input;
    }
}
