package project.toDoListApp;

import project.toDoListApp.utility.FileUtility;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class TaskRegister represents a register that can hold Tasks
 * It is responsible for adding, removing and returning a list of the tasks
 */
public class TaskRegister implements Serializable
{
    private final HashSet<Task> tasks;

    /**
     * Constructor for a task register
     */
    public TaskRegister()
    {
        this.tasks = new HashSet<>();
        try {
            addSavedTasks();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addSavedTasks() throws IOException, ClassNotFoundException {
        File dir = new File("tasks");
        if (dir.exists()){
            File file = new File("tasks/savedTasks.txt");
            if(file.exists()){
                FileUtility fileUtility = new FileUtility();
                TaskRegister register = (TaskRegister) fileUtility.readFromFile("tasks/savedTasks.txt");
                List<Task> taskList = register.getAllTasks();
                this.tasks.addAll(taskList);
            }
        }

    }

    /**
     * Adds a given task to the tasks list
     * @param task The task to add,
     *             can be a duplicate but can not be null
     */
    public void addTask(Task task)
    {
        if (task != null) {
            this.tasks.add(task);
        }
    }

    /**
     * Returns a List of all the tasks present in the register
     * @return A List of all the tasks present in the register in a random order
     */
    public List<Task> getAllTasks()
    {
        return new ArrayList<>(this.tasks);
    }

    /**
     * Returns a List of all the active tasks present in the register
     * @return A List of all the tasks present in the register in a random order
     */
    public List<Task> getAllUncompletedTasks()
    {
        return this.tasks.stream()
                .filter(task -> !task.isStatus())
                .collect(Collectors.toList());
    }

    /**
     * Removes a given task from the register
     * @param task The task to remove,
     *             can not be null
     * @return True if the given task was successfully removed, false otherwise
     */
    public boolean removeTask(Task task)
    {
        // Guard condition
        if (task == null) {
            return false;
        }
        return this.tasks.remove(task);
    }

    /**
     * Returns the number of tasks in the register
     * @return The number of tasks in the register
     */
    public int getNumberOfTasks()
    {
        return this.tasks.size();
    }
}
