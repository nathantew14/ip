package gertrude.exceptions;

/**
 * Represents a custom exception for invalid user input.
 */
public class InvalidInputException extends Exception {
    public InvalidInputException(String message) {
        super(message);
    }
}
