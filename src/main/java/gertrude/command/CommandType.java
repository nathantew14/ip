package gertrude.command;

/**
 * Represents the types of commands in Gertrude.
 */
public enum CommandType {
    ADD_TODO("add:"), REMOVE_TODO("remove:"), LIST_TODOS("list"), COMPLETE_TODO("mark:"), UNCOMPLETE_TODO("unmark:"),
    HELP("help"), UNKNOWN(""), FIND_TODO("find:");

    private final String prefix;

    CommandType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
