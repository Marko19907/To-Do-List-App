package project.toDoListApp.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
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
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import project.toDoListApp.Task;
import project.toDoListApp.controller.Controller;

/**
 * Class ToDoListAppGUI represents the main window in the application.
 */
public class ToDoListAppGUI extends Application {
  private final Controller controller;
  private final ImageLoader imageLoader;

  private final TableView<Task> taskTableView;
  private final HTMLEditor htmlEditor;
  private final TextField taskTitleTextField;
  private final Button dueDateButton;
  private final Label dateLabel;
  private final Label zoomLabel;

  /**
   * ToDoListAppGUI constructor.
   */
  public ToDoListAppGUI() {
    this.controller = new Controller();
    this.imageLoader = new ImageLoader();

    this.taskTableView = new TableView<>();
    this.htmlEditor = new HTMLEditor();
    this.taskTitleTextField = new TextField();
    this.dueDateButton = new Button();
    this.dateLabel = new Label();
    this.zoomLabel = new Label();
  }

  /**
   * The main method.
   */
  public static void main(String[] args) {
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) {
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

    // Disable the center pane on first run
    this.disableCenterPane();

    root.requestFocus();
  }

  /**
   * Sets up the top menu bar.
   *
   * @return The already set up menu bar
   */
  private MenuBar setupTopMenu() {
    MenuBar menuBar = new MenuBar();

    this.setupFileMenu(menuBar);
    this.setupEditMenu(menuBar);
    this.setupViewMenu(menuBar);
    this.setupHelpMenu(menuBar);

    return menuBar;
  }

  /**
   * Sets up the top file menu bar.
   *
   * @param menuBar The already set up file menu bar
   */
  private void setupFileMenu(MenuBar menuBar) {
    Menu fileMenu = new Menu("File");

    menuBar.getMenus().add(fileMenu);

    SeparatorMenuItem separator1 = new SeparatorMenuItem();
    SeparatorMenuItem separator2 = new SeparatorMenuItem();

    MenuItem menuItem1 = new MenuItem("New Reminder");
    KeyCombination keyCombinationNewReminder =
        new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN);
    menuItem1.setAccelerator(keyCombinationNewReminder);
    menuItem1.setOnAction(e -> {
      this.controller.showNewReminderDialog();
      this.refreshTable();
    });

    MenuItem menuItem2 = new MenuItem("Delete Reminder");
    KeyCombination keyCombinationDeleteReminder =
        new KeyCodeCombination(KeyCode.DELETE, KeyCombination.CONTROL_DOWN);
    menuItem2.setAccelerator(keyCombinationDeleteReminder);
    menuItem2.setOnAction(e -> {
      boolean taskDeleted = this.controller.doDeleteReminder();
      if (taskDeleted) {
        this.disableCenterPane();
        this.refreshTable();
      }
    });

    MenuItem menuItem3 = new MenuItem("Save all");
    KeyCombination keyCombinationSaveAll =
        new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
    menuItem3.setAccelerator(keyCombinationSaveAll);
    menuItem3.setOnAction(e -> System.out.println("Save all test"));

    MenuItem menuItem4 = new MenuItem("Exit");
    KeyCombination keyCombinationExit =
        new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
    menuItem4.setAccelerator(keyCombinationExit);
    menuItem4.setOnAction(e -> this.controller.quit(e));

    fileMenu.getItems().addAll(menuItem1, menuItem2, separator1, menuItem3, separator2, menuItem4);
  }

  /**
   * Sets up the top edit menu bar.
   *
   * @param menuBar The already set up edit menu bar
   */
  private void setupEditMenu(MenuBar menuBar) {
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
   * Sets up the top view menu bar.
   *
   * @param menuBar The already set up view menu bar
   */
  private void setupViewMenu(MenuBar menuBar) {
    Menu viewMenu = new Menu("View");

    menuBar.getMenus().add(viewMenu);

    SeparatorMenuItem separator1 = new SeparatorMenuItem();


    MenuItem menuItem1 = new MenuItem("Zoom in");
    KeyCombination keyCombinationZoomIn =
        new KeyCodeCombination(KeyCode.PLUS, KeyCombination.CONTROL_DOWN);
    menuItem1.setAccelerator(keyCombinationZoomIn);
    menuItem1.setOnAction(e -> this.zoomInAction());

    MenuItem menuItem2 = new MenuItem("Zoom out");
    KeyCombination keyCombinationZoomOut =
        new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN);
    menuItem2.setAccelerator(keyCombinationZoomOut);
    menuItem2.setOnAction(e -> this.zoomOutAction());


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
   * Sets up the top help menu bar.
   *
   * @param menuBar The already set up help menu bar
   */
  private void setupHelpMenu(MenuBar menuBar) {
    Menu helpMenu = new Menu("Help");

    menuBar.getMenus().add(helpMenu);

    MenuItem menuItem1 = new MenuItem("About");
    menuItem1.setOnAction(e -> this.controller.showAboutDialog());

    helpMenu.getItems().add(menuItem1);
  }

  /**
   * Sets up the left node.
   *
   * @return The already set up left node
   */
  private VBox setupLeft() {
    VBox vBox = new VBox();

    this.setupLeftTopTable();
    VBox buttonBox = this.setupBottomLeftButtons();
    vBox.getChildren().addAll(this.taskTableView, buttonBox);

    vBox.setPrefWidth(150);
    VBox.setVgrow(this.taskTableView, Priority.ALWAYS);
    return vBox;
  }

  /**
   * Sets up the left table that contains the tasks
   */
  private void setupLeftTopTable() {
    this.taskTableView.setPlaceholder(new Label("No tasks to display"));
    this.taskTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<Task, String> titleColumn = new TableColumn<>("Task Title");
    titleColumn.setCellValueFactory(new PropertyValueFactory<>("taskName"));

    // set on left click action
    this.taskTableView.setOnMouseClicked((MouseEvent event) -> {
      if (event.getButton().equals(MouseButton.PRIMARY)) {
        //TODO: The view class should not be aware of the model, (e.g., the Task class)
        int index = this.taskTableView.getSelectionModel().getSelectedIndex();
        if (index < this.controller.getTaskListWrapper().size() && index >= 0) {
          Task task = this.taskTableView.getItems().get(index);

          this.controller.displayTask(task, this.taskTitleTextField,
              this.htmlEditor, this.dueDateButton, this.dateLabel);
          this.refreshTable();
        }
      }
    });

    this.taskTableView.setItems(this.controller.getTaskListWrapper());
    this.taskTableView.getColumns().add(titleColumn);
    //Set a default sort column
    this.taskTableView.getSortOrder().add(titleColumn);
  }

  /**
   * Sets up a Vbox containing the bottom left buttons.
   *
   * @return an already set-up Vbox containing the bottom left buttons
   */
  private VBox setupBottomLeftButtons() {
    VBox buttonBox = new VBox();

    Button button1 = new Button("New Reminder");
    button1.setOnAction(e -> {
      this.controller.showNewReminderDialog();
      this.refreshTable();
    });
    button1.setPrefWidth(150);
    button1.setAlignment(Pos.CENTER);

    ImageView plusIcon = this.imageLoader.getImage("plus-icon");
    if (plusIcon != null) {
      plusIcon.setFitHeight(12);
      button1.setGraphic(plusIcon);
    }


    Button button2 = new Button("Delete Reminder");
    button2.setPrefWidth(150);
    button2.setAlignment(Pos.CENTER);
    button2.setOnAction(e -> {
      boolean taskDeleted = this.controller.doDeleteReminder();
      if (taskDeleted) {
        this.disableCenterPane();
        this.refreshTable();
      }
    });

    ImageView trashIcon = this.imageLoader.getImage("trash-icon");
    if (trashIcon != null) {
      trashIcon.setFitHeight(12);
      button2.setGraphic(trashIcon);
    }

    buttonBox.getChildren().addAll(button1, button2);
    return buttonBox;
  }

  /**
   * Sets up the center node.
   *
   * @return The already set-up center node
   */
  private VBox setupCenter() {
    VBox vBox = new VBox();

    HBox hBox = this.setupTopCenterHBox();
    HBox bottomZoomHBox = this.setupBottomZoomButtonBox();

    vBox.getChildren().addAll(hBox, this.htmlEditor, bottomZoomHBox);
    VBox.setVgrow(this.htmlEditor, Priority.ALWAYS);
    return vBox;
  }

  /**
   * Sets up the top center HBox.
   *
   * @return The already set-up top center HBox
   */
  private HBox setupTopCenterHBox() {
    HBox hBox = new HBox();

    this.dueDateButton.setOnAction(e -> this.controller.doSetNewEndDate(this.dateLabel));
    this.dueDateButton.setPrefWidth(150);
    this.dueDateButton.setMaxHeight(30);
    this.dueDateButton.setAlignment(Pos.CENTER);

    this.taskTitleTextField.setStyle("-fx-font-size: 20");
    this.taskTitleTextField.setPrefHeight(40);

    VBox dateBox = new VBox();
    dateBox.setAlignment(Pos.CENTER);
    dateBox.getChildren().addAll(this.dateLabel, this.dueDateButton);

    HBox.setHgrow(this.taskTitleTextField, Priority.ALWAYS);
    hBox.getChildren().addAll(this.taskTitleTextField, dateBox);
    return hBox;
  }

  /**
   * Sets up the bottom zoom HBox.
   *
   * @return The already set-up bottom zoom HBox
   */
  private HBox setupBottomZoomButtonBox() {
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER_RIGHT);

    hBox.setPadding(new Insets(0, 15, 0, 15));
    hBox.setSpacing(10);

    Button zoomOutButton = new Button("Zoom out");
    zoomOutButton.setOnAction(e -> this.zoomOutAction());

    Button zoomInButton = new Button("Zoom in");
    zoomInButton.setOnAction(e -> this.zoomInAction());

    this.zoomLabel.setText("100%");
    this.zoomLabel.setAlignment(Pos.CENTER_RIGHT);

    hBox.getChildren().addAll(zoomOutButton, zoomInButton, this.zoomLabel);
    return hBox;
  }

  /**
   * Performs the zoom in action
   */
  private void zoomInAction() {
    this.controller.doZoom(this.htmlEditor, this.zoomLabel, 0.1);
  }

  /**
   * Performs the zoom out action
   */
  private void zoomOutAction() {
    this.controller.doZoom(this.htmlEditor, this.zoomLabel, -0.1);
  }

  /**
   * Disables the given Control
   *
   * @param control The Control item to disable, can be a Button, TextArea, TextField or Label
   */
  private void disableControl(Control control) {
    if (control != null) {
      control.setDisable(true);

      if (control instanceof Labeled) {
        ((Labeled) control).setText("");
      }
      if (control instanceof TextInputControl) {
        ((TextInputControl) control).setText("");
      }
      if (control instanceof HTMLEditor) {
        ((HTMLEditor) control).setHtmlText("");
      }
    }
  }

  /**
   * Disables the center pane TextFields and Buttons
   */
  private void disableCenterPane() {
    this.disableControl(this.taskTitleTextField);
    this.disableControl(this.htmlEditor);
    this.disableControl(this.dueDateButton);
    this.disableControl(this.dateLabel);
  }

  /**
   * Refreshes the table and forces a sort of the data
   */
  private void refreshTable() {
    this.taskTableView.refresh();
    this.taskTableView.sort();
  }
}
