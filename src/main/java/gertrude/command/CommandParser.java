package gertrude.command;

/**
 * Represents a parser for commands in Gertrude.
 */
public class CommandParser {
    /**
     * Parses the user input into a command.
     *
     * @param input the user input
     * @return the parsed command
     */
    public static CommandType parseCommand(String input) {
        String lowerInput = input.toLowerCase();
        for (CommandType type : CommandType.values()) {
            if (type != CommandType.UNKNOWN && lowerInput.startsWith(type.getPrefix())) {
                return type;
            }
            if (type == CommandType.LIST_TODOS && lowerInput.equals(type.getPrefix())) {
                return type;
            }
            if (type == CommandType.FIND_TODO && lowerInput.equals(type.getPrefix())) {
                return type;
            }
        }
        return CommandType.UNKNOWN;
    }
}
