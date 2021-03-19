package project.toDoListApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ToDoListAppGUI extends Application
{
    private final TaskRegister taskRegister;

    public ToDoListAppGUI()
    {
        taskRegister = new TaskRegister();
    }

    /**
     * The main method
     */
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage)
    {
        VBox pane = new VBox();
        Scene scene = new Scene(pane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}
