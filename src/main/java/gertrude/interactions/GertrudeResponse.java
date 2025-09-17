package gertrude.interactions;

/**
 * Represents the response from Gertrude.
 */
public class GertrudeResponse {
    private final String message;
    private final boolean isExit;

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
