public class CompletableTask extends Task {
    private boolean completed = false;

    public CompletableTask(String title) {
        super(title);
    }

    @Override
    public String getTaskType() {
        return "CompletableTask";
    }

    public void toggleCompleted() {
        completed = !completed;
    }

    public void setCompleted() {
        completed = true;
    }

    public void setNotCompleted() {
        completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String format(int index) {
        return (index + 1) + ". " + toString();
    }
    
    public String toFileFormat() {
        return getTaskType() + " | " + (isCompleted() ? "1" : "0") + " | " + getTitle();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s", getTaskType(), completed ? "[X]" : "[ ]", title);
    }
}
