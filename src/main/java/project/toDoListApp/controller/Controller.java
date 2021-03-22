package project.toDoListApp.controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI
 */
public class Controller
{
    public Controller()
    {
    }

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
     * Exits the application. A confirmation dialog is displayed before exiting
     */
    public void quit()
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
        }
    }
}
