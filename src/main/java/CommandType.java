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

    public static CommandType fromInput(String input) {
        String lowerInput = input.toLowerCase();
        for (CommandType type : values()) {
            if (type != UNKNOWN && lowerInput.startsWith(type.getPrefix())) {
                return type;
            }
            if (type == LIST_TODOS && lowerInput.equals(type.getPrefix())) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
