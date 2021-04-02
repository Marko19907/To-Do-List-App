package project.toDoListApp.controller;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;

import project.toDoListApp.Task;

import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI
 */
public class Controller
{
    private Task currentTask;

    public Controller()
    {
    }

    public void displayTask(Task task, TextArea editor)
    {
        if (task != null && editor != null) {
            this.saveCurrentTaskText(editor);

            this.currentTask = task;
            editor.setText(task.getDescription());
        }
    }

    /**
     * Saves the text from the given TextArea to the current Task
     * @param editor The editor to save the text from,
     *               can not be null
     */
    private void saveCurrentTaskText(TextArea editor)
    {
        if (this.currentTask != null && editor != null) {
            this.currentTask.setDescription(editor.getText());
        }
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
