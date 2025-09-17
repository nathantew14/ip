package gertrude.exceptions;

/**
 * Represents a custom exception for invalid date formats.
 */
public class InvalidDateFormatException extends Exception {
    public InvalidDateFormatException(String message) {
        super(message);
    }
}
