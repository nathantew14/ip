package gertrude.command;

public enum TagType {
    BY_TAG("/by"),
    START_TAG("/start"),
    END_TAG("/end");

    private final String tag;

    TagType(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
