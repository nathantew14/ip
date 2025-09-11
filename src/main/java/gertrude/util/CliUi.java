package gertrude.util;

import java.util.Scanner;

public class CliUi {
    private final Scanner scanner;

    public CliUi() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcomeMessage(String message) {
        System.out.println("\n" + message + "\n"
                + "-------------------------------------------------------------------------\n"
                + "If you need help, advice, or just a little chat, I'm always here for you.\n"
                + "Now, what can I do for you today, sweetheart?\n"
                + "-------------------------------------------------------------------------");
    }

    public void showGoodbyeMessage() {
        System.out.println("Gertrude: Goodbye, dear! Take care and come back anytime you need me.");
    }

    public String readCommand() {
        System.out.print("\nYou: ");
        return scanner.nextLine();
    }

    public void showResponse(String response) {
        System.out.println("Gertrude: " + response);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void close() {
        scanner.close();
    }
}
