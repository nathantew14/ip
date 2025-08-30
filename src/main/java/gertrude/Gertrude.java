package gertrude;

import java.io.*;
import gertrude.util.DateTimeParser;
import gertrude.util.Ui;
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
import gertrude.storage.LoadResult;
import gertrude.storage.Storage;
import gertrude.exceptions.InvalidDateFormatException;

public class Gertrude {
    private final String DATA_FILE_PATH = "./data/gertrude.txt"; // Relative path for the data file
    private TaskList tasks = new TaskList();

    enum ReadTaskFileResult {
        SUCCESS,
        NO_FILE_FOUND,
        ERROR_READING_FILE
    }

    private Ui ui;

    public static void main(String[] args) {
        new Gertrude().run();
    }

    private void run() {
        ui = new Ui();
        String welcomeMessage;

        Storage storage = new Storage(DATA_FILE_PATH);
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
            case ERROR_READING_FILE:
                welcomeMessage = "Oh no, dear! I couldn't read your tasks file. Starting fresh for now.";
                break;
            default:
                welcomeMessage = "";
        }

        ui.showWelcomeMessage(welcomeMessage);

        while (true) {
            String input = ui.readCommand();
            if (input.equalsIgnoreCase("bye"))
                break;
            String response = "";
            try {
                response = getResponse(input);
            } catch (InvalidInputException e) {
                response = e.getMessage();
            }

            ui.showResponse(response);
            try {
                storage.saveTasksToFile(tasks.getAllTasks());
            } catch (IOException e) {
                ui.showResponse("Oops! I couldn't save your tasks, dear.");
            }
        }

        ui.showGoodbyeMessage();
        ui.close();
    }

    private String getResponse(String input) throws InvalidInputException, IllegalArgumentException {
        CommandType commandType = CommandParser.parseCommand(input);

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

            case FIND_TODO:
                return handleFindTodo(input);

            case HELP:
                return handleHelp(); // Handle help command
            default:
                return handleHelp();
        }
    }

    private String handleAddTodo(String input) throws InvalidInputException, IllegalArgumentException {
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
            return "Added new event: " + event.getTitle() + " (from: " + event.getStartAsString() + " to: " + event.getEndAsString()
                    + ")";
        } catch (InvalidDateFormatException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }

    private String handleListTodos() {
        if (tasks.isEmpty()) {
            return "No tasks yet, dear!";
        }
        return "Here are your tasks:\n" + tasks.formatTasks();
    }

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

    private void validateTaskIndex(int idx) throws InvalidInputException {
        if (idx < 0 || idx >= tasks.size()) {
            throw new InvalidInputException("Invalid task index, dear!");
        }
    }

    private String handleHelp() {
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
                .append("8. find:<keyword>\n")
                .append("   Find tasks by keyword. Example:\n")
                .append("   find:nemo\n")
                .append("9. help\n")
                .append("   Show this help message.");

        return helpMessage.toString();
    }
}