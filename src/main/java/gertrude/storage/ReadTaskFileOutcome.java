package gertrude.storage;

/**
 * Represents the outcome of reading the task file.
 */
public enum ReadTaskFileOutcome {
    SUCCESS,
    NO_FILE_FOUND,
    FILE_BAD_LINES,
    FILE_UNREADABLE,
}
