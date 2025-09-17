package gertrude.interactions;

/**
 * Represents the response from Gertrude.
 */
public class GertrudeResponse {
    private final String message;
    private final boolean isExit;

    /**
     * Constructs a {@code GertrudeResponse} with the specified message and exit status.
     *
     * @param message The response message to be displayed.
     * @param isExit  {@code true} if this response should trigger application exit; {@code false} otherwise.
     */
    public GertrudeResponse(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    /**
     * Gets the response message.
     *
     * @return the response message
     */
    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }
}
