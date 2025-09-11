package gertrude.storage;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gertrude.exceptions.SaveFileBadLineException;
import gertrude.task.Task;

public class Storage {
    private final String dataFilePath;

    public Storage(String dataFilePath) {
        this.dataFilePath = dataFilePath;
    }

    public LoadResult loadTasksFromFile() {
        File file = new File(dataFilePath);
        if (!file.exists()) {
            return new LoadResult(ReadTaskFileOutcome.NO_FILE_FOUND, new ArrayList<>());
        }

        List<Task> tasks = new ArrayList<>();
        List<String> badLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Task task = Task.fromFileFormat(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (SaveFileBadLineException e) {
                    badLines.add(line);
                }
            }
            if (!badLines.isEmpty()) {
                return new LoadResult(ReadTaskFileOutcome.FILE_BAD_LINES, tasks, badLines);
            }
            return new LoadResult(ReadTaskFileOutcome.SUCCESS, tasks);
        } catch (IOException e) {
            return new LoadResult(ReadTaskFileOutcome.FILE_UNREADABLE, new ArrayList<>());
        }
    }

    public void saveTasksToFile(List<Task> tasks) throws IOException {
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