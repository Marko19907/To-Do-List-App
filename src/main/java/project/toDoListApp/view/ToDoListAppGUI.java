package project.toDoListApp.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import project.toDoListApp.TaskRegister;
import project.toDoListApp.controller.Controller;

public class ToDoListAppGUI extends Application
{
    private final TaskRegister taskRegister;
    private final Controller controller;

    public ToDoListAppGUI()
    {
        this.taskRegister = new TaskRegister();
        this.controller = new Controller();
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
        BorderPane root = new BorderPane();
        root.setTop(this.setupTopMenu());

        stage.setTitle("To-Do List App");
        stage.setMinWidth(300);
        stage.setMinHeight(200);

        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Sets up the top menu bar
     * @return The already set up menu bar
     */
    private MenuBar setupTopMenu()
    {
        MenuBar menuBar = new MenuBar();

        this.setupFileMenu(menuBar);
        this.setupEditMenu(menuBar);
        this.setupViewMenu(menuBar);
        this.setupHelpMenu(menuBar);

        return menuBar;
    }

    /**
     * Sets up the top file menu bar
     * @param menuBar The already set up file menu bar
     */
    private void setupFileMenu(MenuBar menuBar)
    {
        Menu fileMenu = new Menu("File");

        menuBar.getMenus().add(fileMenu);

        SeparatorMenuItem separator1 = new SeparatorMenuItem();
        SeparatorMenuItem separator2 = new SeparatorMenuItem();

        MenuItem menuItem1 = new MenuItem("New Reminder");
        KeyCombination keyCombinationNewReminder = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
        menuItem1.setAccelerator(keyCombinationNewReminder);
        menuItem1.setOnAction(e -> System.out.println("New reminder test"));

        MenuItem menuItem2 = new MenuItem("Delete Reminder");
        KeyCombination keyCombinationDeleteReminder = new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN);
        menuItem2.setAccelerator(keyCombinationDeleteReminder);
        menuItem2.setOnAction(e -> System.out.println("Delete reminder test"));

        MenuItem menuItem3 = new MenuItem("Save all");
        KeyCombination keyCombinationSaveAll = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        menuItem3.setAccelerator(keyCombinationSaveAll);
        menuItem3.setOnAction(e -> System.out.println("Save all test"));

        MenuItem menuItem4 = new MenuItem("Exit");
        KeyCombination keyCombinationExit = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        menuItem4.setAccelerator(keyCombinationExit);
        menuItem4.setOnAction(e -> System.out.println("Exit test"));

        fileMenu.getItems().addAll(menuItem1, menuItem2, separator1, menuItem3, separator2, menuItem4);
    }

    /**
     * Sets up the top edit menu bar
     * @param menuBar The already set up edit menu bar
     */
    private void setupEditMenu(MenuBar menuBar)
    {
        Menu editMenu = new Menu("Edit");

        menuBar.getMenus().add(editMenu);

        SeparatorMenuItem separator1 = new SeparatorMenuItem();

        MenuItem menuItem1 = new MenuItem("Edit font");
        MenuItem menuItem2 = new MenuItem("Edit title color");
        MenuItem menuItem3 = new MenuItem("Edit text color");
        MenuItem menuItem4 = new MenuItem("Preferences");

        editMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, separator1, menuItem4);
    }

    /**
     * Sets up the top view menu bar
     * @param menuBar The already set up view menu bar
     */
    private void setupViewMenu(MenuBar menuBar)
    {
        Menu viewMenu = new Menu("View");

        menuBar.getMenus().add(viewMenu);

        SeparatorMenuItem separator1 = new SeparatorMenuItem();


        MenuItem menuItem1 = new MenuItem("Zoom in");
        KeyCombination keyCombinationZoomIn = new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN);
        menuItem1.setAccelerator(keyCombinationZoomIn);
        menuItem1.setOnAction(e -> System.out.println("Zoom in test"));

        MenuItem menuItem2 = new MenuItem("Zoom out");
        KeyCombination keyCombinationZoomOut = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN);
        menuItem2.setAccelerator(keyCombinationZoomOut);
        menuItem2.setOnAction(e -> System.out.println("Zoom out test"));


        ToggleGroup toggleGroup = new ToggleGroup();

        RadioMenuItem radioItem1 = new RadioMenuItem("Show completed");
        radioItem1.setOnAction(e -> System.out.println("Show completed toggled"));
        radioItem1.setSelected(true);
        radioItem1.setToggleGroup(toggleGroup);

        RadioMenuItem radioItem2 = new RadioMenuItem("Hide completed");
        radioItem2.setOnAction(e -> System.out.println("Hide completed toggled"));
        radioItem2.setSelected(false);
        radioItem2.setToggleGroup(toggleGroup);

        viewMenu.getItems().addAll(radioItem1, radioItem2, separator1, menuItem1, menuItem2);
    }

    /**
     * Sets up the top help menu bar
     * @param menuBar The already set up help menu bar
     */
    private void setupHelpMenu(MenuBar menuBar)
    {
        Menu helpMenu = new Menu("Help");

        menuBar.getMenus().add(helpMenu);

        MenuItem menuItem1 = new MenuItem("About");
        menuItem1.setOnAction(e -> this.controller.showAboutDialog());

        helpMenu.getItems().add(menuItem1);
    }
}
