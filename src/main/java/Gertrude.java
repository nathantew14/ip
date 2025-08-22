// Gertrude.java
import java.util.Scanner;

/**
 * The main class for the Gertrude chatbot application.
 */
public class Gertrude {
    private static final String LINE = "-------------------------------------------------------------------------";
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a Gertrude object with the specified file path for storage.
     */
    public Gertrude() {
        ui = new Ui();
        storage = new Storage();
        tasks = new TaskList(storage.load());
    }

    /**
     * Runs the chatbot until termination.
     */
    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equals("bye")) {
                break;
            }
            String response = getResponse(input);
            System.out.println(response);
        }
        
        ui.showGoodbye();
        scanner.close();
    }

    /**
     * Gets the response for the given user input.
     *
     * @param input The user input.
     * @return The response from Gertrude.
     */
    public String getResponse(String input) {
        try {
            if (input.equals("list")) {
                return tasks.listTasks();
            } else if (input.startsWith("mark:")) {
                return tasks.markTask(input);
            } else if (input.startsWith("unmark:")) {
                return tasks.unmarkTask(input);
            } else if (input.startsWith("remove:")) {
                return tasks.removeTask(input);
            } else if (input.startsWith("add:")) {
                return tasks.addTask(input.substring(4).trim());
            } else if (input.equals("hi")) {
                return "Welcome, dear! I'm Gertrude, your friendly AI chatbot.\n" + 
                       LINE + "\n" +
                       "If you need help, advice, or just a little chat, I'm always here for you.\n" +
                       "Now, what can I do for you today, sweetheart?\n" +
                       LINE;
            } else {
                return "I'm sorry, dear. I don't understand that command. Try 'add', 'list', 'mark', 'unmark', or 'remove'.";
            }
        } catch (GertrudeException e) {
            return "Gertrude: " + e.getMessage();
        }
    }

    /**
     * The main method to start the chatbot.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        new Gertrude().run();
    }
}
