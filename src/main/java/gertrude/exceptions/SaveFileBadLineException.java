package gertrude.exceptions;

public class SaveFileBadLineException extends Exception {
    public SaveFileBadLineException(String line) {
        super("Error reading line: " + line);
    }
}
