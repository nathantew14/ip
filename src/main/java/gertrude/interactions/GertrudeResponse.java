package gertrude.interactions;

public class GertrudeResponse {
    private final String message;
    private final boolean isExit;

    public GertrudeResponse(String message, boolean isExit) {
        this.message = message;
        this.isExit = isExit;
    }

    public String getMessage() {
        return message;
    }

    public boolean isExit() {
        return isExit;
    }
}
