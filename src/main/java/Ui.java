
// Ui.java
/**
 * Handles user interface interactions.
 */
public class Ui {
    private static final String LINE = "-------------------------------------------------------------------------";

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println("Welcome, dear! I'm Gertrude, your friendly AI chatbot.");
        System.out.println(LINE);
        System.out.println("If you need help, advice, or just a little chat, I'm always here for you.");
        System.out.println("Now, what can I do for you today, sweetheart?");
        System.out.println(LINE);
        System.out.println();
    }

    /**
     * Shows the goodbye message.
     */
    public void showGoodbye() {
        System.out.println("Gertrude: Goodbye, dear! Take care and come back anytime you need me.");
    }
}
