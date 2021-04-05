package project.toDoListApp.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import project.toDoListApp.Task;
import project.toDoListApp.controller.Controller;

/**
 * Class ToDoListAppGUI represents the main window in the application.
 */
public class ToDoListAppGUI extends Application
{
    private final Controller controller;
    private final TextArea descriptionTextArea;
    private final TextField taskTitleTextField;

    public ToDoListAppGUI()
    {
        this.controller = new Controller();
        this.descriptionTextArea = new TextArea();
        this.taskTitleTextField = new TextField();
    }

    /**
     * The main method
     */
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        BorderPane root = new BorderPane();
        root.setTop(this.setupTopMenu());
        root.setLeft(this.setupLeft());
        root.setCenter(this.setupCenter());

        stage.setTitle("To-Do List App");
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setOnCloseRequest(e -> this.controller.quit(e));

        Scene scene = new Scene(root, 600, 400, Color.WHITE);
        stage.setScene(scene);
        stage.show();

        root.requestFocus();
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
        menuItem4.setOnAction(e -> this.controller.quit(e));

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

    /**
     * Sets up the left node
     * @return The already set up left node
     */
    private VBox setupLeft()
    {
        VBox vBox = new VBox();

        TableView<Task> table = this.setupLeftTopTable();
        VBox buttonBox = this.setupBottomLeftButtons();
        vBox.getChildren().addAll(table, buttonBox);

        vBox.setPrefWidth(150);
        VBox.setVgrow(table, Priority.ALWAYS);
        return vBox;
    }

    /**
     * Sets up the left table
     * @return The already set up left table
     */
    private TableView<Task> setupLeftTopTable()
    {
        TableView<Task> table = new TableView<>();
        table.setPlaceholder(new Label("No tasks to display"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Task, String> titleColumn = new TableColumn<>("Task Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));

        // set on left click action
        table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                //TODO: The view class should not be aware of the model, (e.g., the Task class)
                int index = table.getSelectionModel().getSelectedIndex();
                if (index < this.controller.getTaskListWrapper().size() && index >= 0) {
                    Task task = table.getItems().get(index);
                    this.controller.displayTask(table, task, this.taskTitleTextField, this.descriptionTextArea);
                }
            }
        });

        table.setItems(this.controller.getTaskListWrapper());
        table.getColumns().add(titleColumn);
        //Set a default sort column
        table.getSortOrder().add(titleColumn);

        return table;
    }

    /**
     * Sets up a Vbox containing the bottom left buttons
     * @return an already set-up Vbox containing the bottom left buttons
     */
    private VBox setupBottomLeftButtons()
    {
        VBox buttonBox = new VBox();

        Button button1 = new Button("New Reminder");
        button1.setOnAction(e -> controller.showNewReminderDialog());
        button1.setPrefWidth(150);

        Button button2 = new Button("Delete Reminder");
        button2.setOnAction(e -> System.out.println("Delete Reminder bottom left button"));
        button2.setPrefWidth(150);

        buttonBox.getChildren().addAll(button1, button2);

        return buttonBox;
    }

    /**
     * Sets up the center node
     * @return The already set-up center node
     */
    private VBox setupCenter()
    {
        VBox vBox = new VBox();
        this.descriptionTextArea.setWrapText(true);

        HBox hBox = this.setupTopCenterHBox();

        vBox.getChildren().addAll(hBox, this.descriptionTextArea);
        VBox.setVgrow(this.descriptionTextArea, Priority.ALWAYS);
        return vBox;
    }

    /**
     * Sets up the top center HBox
     * @return The already set-up top center HBox
     */
    private HBox setupTopCenterHBox()
    {
        HBox hBox = new HBox();

        Button setDueDateButton = new Button("Due date:");
        setDueDateButton.setOnAction(e -> System.out.println("Due date top center button test"));
        setDueDateButton.setPrefWidth(150);
        setDueDateButton.setMaxHeight(250);

        HBox.setHgrow(this.taskTitleTextField, Priority.ALWAYS);
        this.taskTitleTextField.setStyle("-fx-font-size: 18");

        hBox.getChildren().addAll(this.taskTitleTextField, setDueDateButton);
        return hBox;
    }
}
