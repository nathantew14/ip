public enum CommandType {
    ADD_TODO("add:"),
    REMOVE_TODO("remove:"),
    LIST_TODOS("list"),
    COMPLETE_TODO("mark:"),
    UNCOMPLETE_TODO("unmark:"),
    HELP("help"),
    UNKNOWN("");

    private final String prefix;

    CommandType(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
