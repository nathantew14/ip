public class CommandParser {
    public static CommandType parseCommand(String input) {
        String lowerInput = input.toLowerCase();
        for (CommandType type : CommandType.values()) {
            if (type != CommandType.UNKNOWN && lowerInput.startsWith(type.getPrefix())) {
                return type;
            }
            if (type == CommandType.LIST_TODOS && lowerInput.equals(type.getPrefix())) {
                return type;
            }
        }
        return CommandType.UNKNOWN;
    }
}
