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

    @Override
    public String toString() {
        return getTitlePrefix() + title;
    }
}
