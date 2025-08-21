import java.util.Scanner;

public class Gertrude {
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
        return input;
    }
}
