package gertrude.util;

import java.util.Scanner;

/**
 * Represents the CLI user interface for Gertrude.
 */
public class CliUi {
    private final Scanner scanner;

    public CliUi() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays a welcome message to the user.
     *
     * @param message the welcome message to display
     */
    public void showWelcomeMessage(String message) {
        System.out.println(
                "\n" + message + "\n" + "-------------------------------------------------------------------------\n"
                        + "If you need help, advice, or just a little chat, I'm always here for you.\n"
                        + "Now, what can I do for you today, sweetheart?\n"
                        + "-------------------------------------------------------------------------");
    }

    /**
     * Displays a goodbye message to the user.
     */
    public void showGoodbyeMessage() {
        System.out.println("Gertrude: Goodbye, dear! Take care and come back anytime you need me.");
    }

    /**
     * Reads a command from the user.
     *
     * @return the command entered by the user
     */
    public String readCommand() {
        System.out.print("\nYou: ");
        return scanner.nextLine();
    }

    /**
     * Displays a response to the user.
     *
     * @param response the response to display
     */
    public void showResponse(String response) {
        System.out.println("Gertrude: " + response);
    }

    /**
     * Displays a message to the user.
     *
     * @param message the message to display
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Closes the scanner resource.
     */
    public void close() {
        scanner.close();
    }
}
