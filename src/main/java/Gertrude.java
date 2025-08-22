import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Gertrude {
    private static final String ADD_TODO_PREFIX = "add todo:";
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
        
        return input;
    }
}
