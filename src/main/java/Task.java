public abstract class Task {
    protected String title;
    protected boolean completed = false;

    public Task(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTaskType() {
        return "Task";
    }

    public String getTitlePrefix() {
        return String.format("[%s] ", getTaskType());
    }

    public abstract String format(int index);

    public abstract String toFileFormat(); // Convert task to file format

    public static Task fromFileFormat(String line) {
        try {
            String[] parts = line.split(" \\| ");
            String type = parts[0];
            boolean isCompleted = parts[1].equals("1");
            String title = parts[2];

            switch (type) {
                case "T":
                    Todo todo = new Todo(title);
                    if (isCompleted) todo.setCompleted();
                    return todo;
                case "D":
                    String deadline = parts[3];
                    Deadline deadlineTask = new Deadline(title, deadline);
                    if (isCompleted) deadlineTask.setCompleted();
                    return deadlineTask;
                case "E":
                    String start = parts[3];
                    String end = parts[4];
                    Event event = new Event(title, start, end);
                    if (isCompleted) event.setCompleted();
                    return event;
                default:
                    return null;
            }
        } catch (Exception e) {
            return null; // Handle corrupted data gracefully
        }
    }

    @Override
    public String toString() {
        return getTitlePrefix() + title;
    }
}
