package project.toDoListApp;

import java.util.HashSet;

public class TaskRegister {
    private final HashSet<Task> tasks;

    /**
     * Constructor for a task register
     */
    public TaskRegister() {
        this.tasks = new HashSet<>();
    }

    /**
     * Adds a task to the lis
     *
     * @param task the task to be added
     */
    public void addTask(Task task) {
        if (null == task) {
            throw new NullPointerException("Input can not be null");
        }

        this.tasks.add(task);
    }
}
