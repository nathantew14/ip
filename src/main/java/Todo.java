public class Todo extends CompletableTask {
    public Todo(String title) {
        super(title);
    }

    @Override
    public String getTaskType() {
        return "T";
    }
}
