package gertrude.exceptions;

/**
 * Represents a custom exception for bad lines in the save file.
 */
public class SaveFileBadLineException extends Exception {
    public SaveFileBadLineException(String line) {
        super("Error reading line: " + line);
    }
}
