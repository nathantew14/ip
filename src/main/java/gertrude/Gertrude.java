package gertrude;

import java.io.*;
import gertrude.util.DateTimeParser;
import gertrude.util.CliUi;
import gertrude.command.CommandParser;
import gertrude.command.CommandType;
import gertrude.command.TagType;
import gertrude.task.TaskList;
import gertrude.task.Todo;
import gertrude.task.Deadline;
import gertrude.task.Event;
import gertrude.task.Task;
import gertrude.task.CompletableTask;
import gertrude.exceptions.InvalidInputException;
import gertrude.interactions.GertrudeResponse;
import gertrude.storage.LoadResult;
import gertrude.storage.Storage;
import gertrude.exceptions.InvalidDateFormatException;

/**
 * The main class for the Gertrude chatbot application. Handles user input, task
 * management, and file storage.
 */
public class Gertrude {
    private final String DATA_FILE_PATH;
    private TaskList tasks = new TaskList(); // TaskList is initialised as it is always used
    private Storage storage; // File path is not yet available to initialise storage
    private CliUi cliUi; // command line interface is not always used

    /**
     * Constructs a Gertrude instance with the specified data file path.
     *
     * @param filePath The path to the data file for storing tasks.
     */
    public Gertrude(String filePath, String... args) {
        DATA_FILE_PATH = filePath;
    }

    public Gertrude() {
        this("data/gertrude.txt");
    }

    /**
     * The main entry point for the application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String... args) {
        new Gertrude().run();
    }

    public String init() {
        String welcomeMessage;

        assert DATA_FILE_PATH != null && !DATA_FILE_PATH.isEmpty() : "DATA_FILE_PATH missing";

        storage = new Storage(DATA_FILE_PATH);
        LoadResult loadResult = storage.loadTasksFromFile();

        switch (loadResult.getStatus()) {
        case SUCCESS:
            tasks = new TaskList(loadResult.getTasks());
            welcomeMessage = "Welcome back, dear! I've loaded your tasks from the last session.";
            break;
        case NO_FILE_FOUND:
            welcomeMessage = "Hello, dear! It seems like this is your first time here.\n"
                    + "I'm Gertrude, your friendly AI chatbot. Let's get started!";
            break;
        case FILE_BAD_LINES:
            tasks = new TaskList(loadResult.getTasks());
            StringBuilder badLinesMessage = new StringBuilder();
            badLinesMessage.append("Oh dear, I found some issues while reading your tasks file.\n");
            badLinesMessage.append("The following lines were skipped due to errors:\n");
            for (String badLine : loadResult.getBadLines()) {
                badLinesMessage.append("- ").append(badLine).append("\n");
            }
            welcomeMessage = badLinesMessage.toString();
            break;
        case FILE_UNREADABLE:
            welcomeMessage = "Oh no, dear! I couldn't read your tasks file. Starting fresh for now.";
            break;
        default:
            welcomeMessage = "";
        }
        return welcomeMessage;
    }

    /**
     * Runs the main application loop.
     */
    private void run() {
        String welcomeMessage = init();
        assert welcomeMessage != null && !welcomeMessage.isEmpty() : "Missing welcome message";

        cliUi = new CliUi();
        cliUi.showWelcomeMessage(welcomeMessage);

        while (true) {
            String input = cliUi.readCommand();
            GertrudeResponse response = getResponse(input);
            cliUi.showResponse(response.getMessage());
            if (response.isExit()) {
                break;
            }
        }
        cliUi.close();
    }

    /**
     * Processes user input and returns a response.
     *
     * @param input The user input.
     * @return The response to the user input.
     */
    public GertrudeResponse getResponse(String input) {
        if (input.equalsIgnoreCase("bye")) {
            return new GertrudeResponse("Goodbye! Have a great day!", true);
        }
        CommandType commandType = CommandParser.parseCommand(input);
        String response;
        try {
            switch (commandType) {
            case ADD_TODO:
                response = handleAddTodo(input);
                break;

            case LIST_TODOS:
                response = handleListTodos();
                break;

            case COMPLETE_TODO:
                response = handleCompleteTodo(input);
                break;

            case UNCOMPLETE_TODO:
                response = handleUncompleteTodo(input);
                break;

            case REMOVE_TODO:
                response = handleRemoveTodo(input);
                break;

            case FIND_TODO:
                response = handleFindTodo(input);
                break;

            case HELP:
                response = handleHelp(); // Handle help command
                break;

            default:
                response = handleHelp();
            }
        } catch (InvalidInputException | IllegalArgumentException e) {
            return new GertrudeResponse(e.getMessage(), false);
        }

        try {
            storage.saveTasksToFile(tasks.getAllTasks());
        } catch (IOException e) {
            response = "(Oops! I couldn't save your tasks: " + e.getMessage() + ")\n" + response;
        }
        return new GertrudeResponse(response, false);
    }

    /**
     * Handles the addition of a todo task.
     *
     * @param input The user input.
     * @return A response indicating the task was added.
     * @throws InvalidInputException    If the input is invalid.
     * @throws IllegalArgumentException If an illegal argument is encountered.
     */
    private String handleAddTodo(String input) throws InvalidInputException, IllegalArgumentException {
        String content = input.substring(CommandType.ADD_TODO.getPrefix().length()).trim();

        if (content.isEmpty()) {
            throw new InvalidInputException("Please provide a title for the todo.");
        }

        int byIndex = content.indexOf(TagType.BY_TAG.getTag());
        int startIndex = content.indexOf(TagType.START_TAG.getTag());
        int endIndex = content.indexOf(TagType.END_TAG.getTag());

        // Check for invalid combinations
        if ((byIndex != -1 && (startIndex != -1 || endIndex != -1)) || (startIndex != -1 && endIndex == -1)
                || (endIndex != -1 && startIndex == -1)) {
            throw new InvalidInputException(
                    "Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
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

        throw new InvalidInputException(
                "Invalid combination of tags. Please use only /by for deadlines, or both /start and /end for events.");
    }

    /**
     * Creates a deadline task from user input.
     *
     * @param content The user input content.
     * @param byIndex The index of the "/by" tag in the input.
     * @return A response indicating the deadline task was added.
     * @throws InvalidInputException If the input is invalid.
     */
    private String createDeadlineTask(String content, int byIndex) throws InvalidInputException {
        String title = content.substring(0, byIndex).trim();
        String deadline = content.substring(byIndex + TagType.BY_TAG.getTag().length()).trim();

        if (title.isEmpty() || deadline.isEmpty()) {
            throw new InvalidInputException("Please provide both a title and a deadline.");
        }

        try {
            Deadline dl = new Deadline(title, deadline);
            tasks.add(dl);
            return "Added new deadline: " + dl.getTitle() + " (by: " + dl.getDeadlineAsString() + ")";
        } catch (InvalidDateFormatException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Creates an event task from user input.
     *
     * @param content    The user input content.
     * @param startIndex The index of the "/start" tag in the input.
     * @param endIndex   The index of the "/end" tag in the input.
     * @return A response indicating the event task was added.
     * @throws InvalidInputException If the input is invalid.
     */
    private String createEventTask(String content, int startIndex, int endIndex) throws InvalidInputException {
        String title = content.substring(0, startIndex).trim();
        String start = content.substring(startIndex + TagType.START_TAG.getTag().length(), endIndex).trim();
        String end = content.substring(endIndex + TagType.END_TAG.getTag().length()).trim();

        if (title.isEmpty() || start.isEmpty() || end.isEmpty()) {
            throw new InvalidInputException("Please provide a title, start, and end for the event.");
        }

        try {
            Event event = new Event(title, start, end);
            tasks.add(event);
            return "Added new event: " + event.getTitle() + " (from: " + event.getStartAsString() + " to: "
                    + event.getEndAsString() + ")";
        } catch (InvalidDateFormatException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    /**
     * Handles the listing of all tasks.
     *
     * @return A response listing all tasks.
     */
    private String handleListTodos() {
        if (tasks.isEmpty()) {
            return "No tasks yet, dear!";
        }
        return "Here are your tasks:\n" + tasks.formatTasks();
    }

    /**
     * Handles marking a task as completed.
     *
     * @param input The user input.
     * @return A response indicating the task was marked as completed.
     * @throws InvalidInputException If the input is invalid.
     */
    private String handleCompleteTodo(String input) throws InvalidInputException {
        String idxStr = input.substring(CommandType.COMPLETE_TODO.getPrefix().length()).trim();

        try {
            int idx = Integer.parseInt(idxStr) - 1;
            validateTaskIndex(idx);

            Task t = tasks.getByIndex(idx);
            if (!(t instanceof CompletableTask)) {
                throw new InvalidInputException("Task #" + (idx + 1) + " cannot be marked as completed.");
            }

            ((CompletableTask) t).setCompleted();
            return "Marked task #" + (idx + 1) + " as completed.";

        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please provide a valid task index to complete.");
        }
    }

    /**
     * Handles marking a task as not completed.
     *
     * @param input The user input.
     * @return A response indicating the task was marked as not completed.
     * @throws InvalidInputException If the input is invalid.
     */
    private String handleUncompleteTodo(String input) throws InvalidInputException {
        String idxStr = input.substring(CommandType.UNCOMPLETE_TODO.getPrefix().length()).trim();

        try {
            int idx = Integer.parseInt(idxStr) - 1;
            validateTaskIndex(idx);

            Task t = tasks.getByIndex(idx);
            if (!(t instanceof CompletableTask)) {
                throw new InvalidInputException("Task #" + (idx + 1) + " cannot be marked as not completed.");
            }

            CompletableTask ct = (CompletableTask) t;
            if (!ct.isCompleted()) {
                throw new InvalidInputException("Task #" + (idx + 1) + " is already not completed.");
            }

            ct.setNotCompleted();
            return "Marked task #" + (idx + 1) + " as not completed.";

        } catch (NumberFormatException e) {
            throw new InvalidInputException("Please provide a valid task index to uncomplete.");
        }
    }

    /**
     * Handles removing a task.
     *
     * @param input The user input.
     * @return A response indicating the task was removed.
     * @throws InvalidInputException If the input is invalid.
     */
    private String handleRemoveTodo(String input) throws InvalidInputException {
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

    private String handleFindTodo(String input) throws InvalidInputException {
        String keyword = input.substring(CommandType.FIND_TODO.getPrefix().length()).trim();

        if (keyword.isEmpty()) {
            throw new InvalidInputException("Please provide a keyword to search for.");
        }

        TaskList foundTasks = tasks.find(keyword);

        if (foundTasks.isEmpty()) {
            return "No tasks found containing: " + keyword;
        }

        return "Here are the tasks matching your search:\n" + foundTasks.formatTasks();
    }

    /**
     * Validates the task index provided by the user.
     *
     * @param idx The task index.
     * @throws InvalidInputException If the index is invalid.
     */
    private void validateTaskIndex(int idx) throws InvalidInputException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new InvalidInputException("Invalid task index, dear!");
        }
    }

    /**
     * Handles displaying the help message.
     *
     * @return The help message.
     */
    private String handleHelp() {
        StringBuilder helpMessage = new StringBuilder("Here are the available commands:\n")
                .append("1. add:<description>\n").append("   Add a todo. Example:\n").append("   add:find nemo\n")
                .append("2. add:<description> /by <deadline>\n").append("   Add a deadline. Examples:\n")
                .append("   add:finish iP /by 2/12/2019 1800\n").append("   add:finish iP /by 2/12/2019 6:00am\n")
                .append("   add:finish iP /by 2019-12-02 18:00\n").append("   add:finish iP /by 2019-12-02\n")
                .append("   Supported date formats:\n");

        for (String format : DateTimeParser.getAvailableFormats()) {
            helpMessage.append("   - ").append(format).append("\n");
        }

        helpMessage.append("3. add:<description> /start <start time> /end <end time>\n")
                .append("   Add an event with a start and end time. Example:\n")
                .append("   add:exco meeting /start 2/12/2019 5:00pm /end 2/12/2019 6:00pm\n").append("4. list\n")
                .append("   List all tasks.\n").append("5. mark:<task id>\n")
                .append("   Mark a task as completed. Example:\n").append("   mark:2\n").append("6. unmark:<task id>\n")
                .append("   Unmark a task as not completed. Example:\n").append("   unmark:2\n")
                .append("7. remove:<task id>\n").append("   Remove a task. Example:\n").append("   remove:2\n")
                .append("8. find:<keyword>\n").append("   Find tasks by keyword. Example:\n").append("   find:nemo\n")
                .append("9. help\n").append("   Show this help message.");

        return helpMessage.toString();
    }
}