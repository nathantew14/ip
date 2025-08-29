import java.io.*;
import java.util.ArrayList;
import java.util.List;
import util.DateTimeParser;

public class Gertrude {
    // Define enums for command types and tags
    enum CommandType {
        ADD_TODO("add:"),
        REMOVE_TODO("remove:"),
        LIST_TODOS("list"),
        COMPLETE_TODO("mark:"),
        UNCOMPLETE_TODO("unmark:"),
        HELP("help"), // New help command
        UNKNOWN(""); // For unknown commands
        
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
    
    enum TagType {
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
    
    private static final String DATA_FILE_PATH = "./data/gertrude.txt"; // Relative path for the data file
    private static List<Task> tasks = new ArrayList<>();

    enum ReadTaskFileResult {
        SUCCESS,
        NO_FILE_FOUND,
        ERROR_READING_FILE
    }

    static Ui ui;

    public static void main(String[] args) {
        ui = new Ui();
        String welcomeMessage;

        ReadTaskFileResult loadResult = loadTasksFromFile(); // Load tasks from file at startup

        switch (loadResult) {
            case SUCCESS:
                welcomeMessage = "Welcome back, dear! I've loaded your tasks from the last session.";
                break;
            case NO_FILE_FOUND:
                welcomeMessage = "Hello, dear! It seems like this is your first time here.\n"
                        + "I'm Gertrude, your friendly AI chatbot. Let's get started!";
                break;
            case ERROR_READING_FILE:
                welcomeMessage = "Oh no, dear! I couldn't read your tasks file. Starting fresh for now.";
                break;
            default:
                welcomeMessage = "";
        }

        ui.showWelcomeMessage(welcomeMessage);

        while (true) {
            String input = ui.readCommand();
            if (input.equalsIgnoreCase("bye")) break;
            String response = "";
            try {
                response = getResponse(input);
            } catch (InvalidInputException e) {
                response = e.getMessage();
            } finally {
                ui.showResponse(response);
                saveTasksToFile(); // Save tasks to file after each interaction
            }
        }

        ui.showGoodbyeMessage();
        ui.close();
    }

    private static void saveTasksToFile() {
        try {
            File file = new File(DATA_FILE_PATH);
            file.getParentFile().mkdirs(); // Ensure the parent directory exists
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            for (Task task : tasks) {
                writer.write(task.toFileFormat());
                writer.newLine();
            }

            writer.close();
        } catch (IOException e) {
            ui.showResponse("Oops! I couldn't save your tasks, dear.");
        }
    }

    private static ReadTaskFileResult loadTasksFromFile() {
        File file = new File(DATA_FILE_PATH);
        if (!file.exists()) {
            return ReadTaskFileResult.NO_FILE_FOUND; // No file to load from
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = Task.fromFileFormat(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
            return ReadTaskFileResult.SUCCESS;
        } catch (IOException e) {
            return ReadTaskFileResult.ERROR_READING_FILE;
        }
    }

    private static String getResponse(String input) throws InvalidInputException, IllegalArgumentException {
        CommandType commandType = CommandType.fromInput(input);

        switch (commandType) {
            case ADD_TODO:
                return handleAddTodo(input);

            case LIST_TODOS:
                return handleListTodos();

            case COMPLETE_TODO:
                return handleCompleteTodo(input);

            case UNCOMPLETE_TODO:
                return handleUncompleteTodo(input);

            case REMOVE_TODO:
                return handleRemoveTodo(input);

            case HELP:
                return handleHelp(); // Handle help command
            default:
                return handleHelp();
        }
    }
    
    private static String handleAddTodo(String input) throws InvalidInputException, IllegalArgumentException {
        String content = input.substring(CommandType.ADD_TODO.getPrefix().length()).trim();
        
        if (content.isEmpty()) {
            throw new InvalidInputException("Please provide a title for the todo.");
        }
        
        int byIndex = content.indexOf(TagType.BY_TAG.getTag());
        int startIndex = content.indexOf(TagType.START_TAG.getTag());
        int endIndex = content.indexOf(TagType.END_TAG.getTag());

            // Check for invalid combinations
            if ((byIndex != -1 && (startIndex != -1 || endIndex != -1)) ||
                (startIndex != -1 && endIndex == -1) ||
                (endIndex != -1 && startIndex == -1)) {
                throw new InvalidInputException("Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
            }

        // Handle deadline task
        if (byIndex != -1) {
            return createDeadlineTask(content, byIndex);
        }
        
        // Handle event task
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            return createEventTask(content, startIndex, endIndex);
        }
        
        // Handle simple todo task
        if (startIndex == -1 && endIndex == -1) {
            Todo todo = new Todo(content);
            tasks.add(todo);
            return "Added new todo: " + todo.getTitle();
        }
    
        throw new InvalidInputException("Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
    }
    
    private static String createDeadlineTask(String content, int byIndex) throws InvalidInputException {
        String title = content.substring(0, byIndex).trim();
        String deadline = content.substring(byIndex + TagType.BY_TAG.getTag().length()).trim();

        if (title.isEmpty() || deadline.isEmpty()) {
            throw new InvalidInputException("Please provide both a title and a deadline.");
        }

        try {
            Deadline dl = new Deadline(title, deadline);
            tasks.add(dl);
            return "Added new deadline: " + dl.getTitle() + " (by: " + dl.getDeadline() + ")";
        } catch (InvalidDateFormatException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    private static String createEventTask(String content, int startIndex, int endIndex) throws InvalidInputException {
        String title = content.substring(0, startIndex).trim();
        String start = content.substring(startIndex + TagType.START_TAG.getTag().length(), endIndex).trim();
        String end = content.substring(endIndex + TagType.END_TAG.getTag().length()).trim();

        if (title.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new InvalidInputException("Please provide a title, start, and end for the event.");
        }

        try {
            Event event = new Event(title, start, end);
            tasks.add(event);
            return "Added new event: " + event.getTitle() + " (from: " + event.getStart() + " to: " + event.getEnd() + ")";
        } catch (InvalidDateFormatException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }
    
    private static String handleListTodos() {
        if (tasks.isEmpty()) {
            return "No tasks yet, dear!";
        }
        
        StringBuilder sb = new StringBuilder("Here are your tasks:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(tasks.get(i).format(i)).append("\n");
        }
        return sb.toString().trim();
    }
    
    private static String handleCompleteTodo(String input) throws InvalidInputException {
        String idxStr = input.substring(CommandType.COMPLETE_TODO.getPrefix().length()).trim();
        
        try {
            int idx = Integer.parseInt(idxStr) - 1;
            validateTaskIndex(idx);
            
            Task t = tasks.get(idx);
            if (!(t instanceof CompletableTask)) {
                throw new InvalidInputException("Task #" + (idx + 1) + " cannot be marked as completed.");
            }
            
            ((CompletableTask)t).setCompleted();
            return "Marked task #" + (idx + 1) + " as completed.";
            
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please provide a valid task index to complete.");
        }
    }
    
    private static String handleUncompleteTodo(String input) throws InvalidInputException {
        String idxStr = input.substring(CommandType.UNCOMPLETE_TODO.getPrefix().length()).trim();
        
        try {
            int idx = Integer.parseInt(idxStr) - 1;
            validateTaskIndex(idx);
            
            Task t = tasks.get(idx);
            if (!(t instanceof CompletableTask)) {
                throw new InvalidInputException("Task #" + (idx + 1) + " cannot be marked as not completed.");
            }
            
            CompletableTask ct = (CompletableTask)t;
            if (!ct.isCompleted()) {
                throw new InvalidInputException("Task #" + (idx + 1) + " is already not completed.");
            }
            
            ct.setNotCompleted();
            return "Marked task #" + (idx + 1) + " as not completed.";
            
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please provide a valid task index to uncomplete.");
        }
    }
    
    private static String handleRemoveTodo(String input) throws InvalidInputException {
        String idxStr = input.substring(CommandType.REMOVE_TODO.getPrefix().length()).trim();
        
        if (tasks.isEmpty()) {
            throw new InvalidInputException("No tasks to remove, dear!");
        }
        
        try {
            int idx = Integer.parseInt(idxStr) - 1;
            validateTaskIndex(idx);
            
            Task removed = tasks.remove(idx);
            return "Removed task #" + (idx + 1) + ": " + removed.getTitle();
            
        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please provide a valid task index to remove.");
        }
    }
    
    private static void validateTaskIndex(int idx) throws InvalidInputException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new InvalidInputException("Invalid task index, dear!");
        }
    }
    
    private static String handleHelp() {
        StringBuilder helpMessage = new StringBuilder("Here are the available commands:\n")
            .append("1. add:<description>\n")
            .append("   Add a todo. Example:\n")
            .append("   add:find nemo\n")
            .append("2. add:<description> /by <deadline>\n")
            .append("   Add a deadline. Examples:\n")
            .append("   add:finish iP /by 2/12/2019 1800\n")
            .append("   add:finish iP /by 2/12/2019 6:00am\n")
            .append("   add:finish iP /by 2019-12-02 18:00\n")
            .append("   add:finish iP /by 2019-12-02\n")
            .append("   Supported date formats:\n");

        for (String format : DateTimeParser.getAvailableFormats()) {
            helpMessage.append("   - ").append(format).append("\n");
        }

        helpMessage.append("3. add:<description> /start <start time> /end <end time>\n")
            .append("   Add an event with a start and end time. Example:\n")
            .append("   add:exco meeting /start 2/12/2019 5:00pm /end 2/12/2019 6:00pm\n")
            .append("4. list\n")
            .append("   List all tasks.\n")
            .append("5. mark:<task id>\n")
            .append("   Mark a task as completed. Example:\n")
            .append("   mark:2\n")
            .append("6. unmark:<task id>\n")
            .append("   Unmark a task as not completed. Example:\n")
            .append("   unmark:2\n")
            .append("7. remove:<task id>\n")
            .append("   Remove a task. Example:\n")
            .append("   remove:2\n")
            .append("8. help\n")
            .append("   Show this help message.");

        return helpMessage.toString();
    }
}