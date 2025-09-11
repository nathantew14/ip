package gertrude.storage;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<Task> tasks = reader.lines()
                    .map(Task::fromFileFormat)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return new LoadResult(ReadTaskFileOutcome.SUCCESS, tasks);
        } catch (IOException e) {
            return new LoadResult(ReadTaskFileOutcome.ERROR_READING_FILE, new ArrayList<>());
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