package project.toDoListApp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import project.toDoListApp.Task;
import project.toDoListApp.TaskRegister;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI
 */
public class Controller
{
    private final TaskRegister taskRegister;
    private final ObservableList<Task> taskListWrapper;

    private Task currentTask;

    public Controller()
    {
        this.taskRegister = new TaskRegister();
        this.taskListWrapper = FXCollections.observableArrayList(this.taskRegister.getAllTasks());

        this.fillRegisterWithTestTasks();
    }

    public void displayTask(TableView<Task> table, Task task, TextField taskTitle, TextArea editor)
    {
        if (table != null && task != null && taskTitle != null && editor != null) {
            this.saveTaskToRegister(taskTitle, editor);

            this.currentTask = task;
            editor.setText(task.getDescription());
            taskTitle.setText(task.getTaskName());

            table.refresh();
            table.sort();
        }
    }

    /**
     * Saves the current task to the register
     * @param editor    The TextArea to save the text from,
     *                  can not be null
     * @param taskTitle The TextField to get the text from,
     *                  can not be null
     */
    private void saveTaskToRegister(TextField taskTitle, TextArea editor)
    {
        if (this.currentTask != null && taskTitle != null && editor != null) {
            if (taskTitle.getText().isBlank()) {
                //TODO: The TextField is blank, the task will throw an exception
                //Do nothing for now
            }
            else {
                this.currentTask.setTaskName(taskTitle.getText());
            }
            this.currentTask.setDescription(editor.getText());

            this.taskRegister.addTask(this.currentTask);
            this.updateObservableList();
        }
    }

    /**
     * Updates the observable list of tasks with fresh values from the task register
     */
    private void updateObservableList()
    {
        this.taskListWrapper.setAll(this.taskRegister.getAllTasks());
    }

    /**
     * Adds a few tasks to the register for testing
     */
    private void fillRegisterWithTestTasks()
    {
        this.taskRegister.addTask(new Task("Title 1", "Desc 1",
                "None", LocalDate.parse("2100-12-01")));
        this.taskRegister.addTask(new Task("Title 2", "Desc 2",
                "Cooking", LocalDate.parse("3100-12-01")));

        this.updateObservableList();
    }

    /**
     * Returns an ObservableList that holds the tasks
     * @return an ObservableList that holds the tasks
     */
    public ObservableList<Task> getTaskListWrapper()
    {
        return this.taskListWrapper;
    }

    // -----------------------------------------------------------
    //    DIALOGS
    // -----------------------------------------------------------

    /**
     * Shows the About Dialog
     */
    public void showAboutDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("To-Do List App");
        alert.setContentText("An application created by" + "\n"
                + "Group 2" + "\n\n"
                + "2021");

        alert.showAndWait();
    }

    /**
     * Application exit dialog. A confirmation dialog that is displayed before exiting
     */
    public void quit(Event event)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm close");
        alert.setHeaderText("Exit this application?");
        alert.setContentText("Are you sure you want to exit the application?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                //TODO: Save the app's state to disk before exiting?
                Platform.exit();
            }
            else {
                event.consume();
            }
        }
    }
}
