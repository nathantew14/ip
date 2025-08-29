public class CommandParser {
    public static CommandType parseCommand(String input) {
        return CommandType.fromInput(input);
    }
}
