package project.toDoListApp.controller;

import javafx.scene.control.Alert;

public class Controller
{
    public Controller()
    {
    }

    public void showAboutDialog()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("To-Do List App");
        alert.setContentText("An application created by" + "\n"
                + "Group 2" +"\n\n"
                + "2021");

        alert.showAndWait();
    }
}
