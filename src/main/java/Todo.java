public class Todo {
    private String title;
    private boolean completed = false;

    public Todo(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
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
        String status = completed ? "[X]" : "[ ]";
        return (index + 1) + ". " + status + " " + title;
    }
}
