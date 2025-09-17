package gertrude.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gertrude.exceptions.SaveFileBadLineException;
import gertrude.task.Task;

/**
 * Handles the storage of tasks to and from a file.
 */
public class Storage {
    private final String dataFilePath;

    public Storage(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    /**
     * Loads the task list from the file.
     *
     * @return the result of the load operation, including the loaded tasks
     */
    public LoadResult loadTasksFromFile() {
        assert dataFilePath != null && !dataFilePath.isEmpty() : "Missing data file path";
        File file = new File(dataFilePath);
        if (!file.exists()) {
            return new LoadResult(ReadTaskFileOutcome.NO_FILE_FOUND, new ArrayList<>());
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Task> tasks = new ArrayList<>();
            List<String> invalidLines = new ArrayList<>();

            reader.lines().forEach(line -> {
                try {
                    Task task = Task.fromFileFormat(line);
                    tasks.add(task);
                } catch (SaveFileBadLineException e) {
                    invalidLines.add(line);
                }
            });
            return new LoadResult(ReadTaskFileOutcome.SUCCESS, tasks);
        } catch (IOException e) {
            return new LoadResult(ReadTaskFileOutcome.FILE_UNREADABLE, new ArrayList<>());
        }
    }

    /**
     * Saves the task list to the file.
     *
     * @param tasks the task list to save
     */
    public void saveTasksToFile(List<Task> tasks) throws IOException {
        assert dataFilePath != null && !dataFilePath.isEmpty() : "Missing data file path";
        File file = new File(dataFilePath);
        file.getParentFile().mkdirs(); // Ensure the parent directory exists
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));

        for (Task task : tasks) {
            writer.write(task.toFileFormat());
            writer.newLine();
        }

        writer.close();
    }
}
