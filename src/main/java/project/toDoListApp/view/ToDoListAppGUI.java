package project.toDoListApp.view;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
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
import java.util.Arrays;

/**
 * Class ToDoListAppGUI represents the main window in the application.
 */
public class ToDoListAppGUI extends Application {
  private final Controller controller;
  private final ImageLoader imageLoader;

  private final BorderPane root;
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

    this.root = new BorderPane();
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
    this.root.setTop(this.setupTopMenu());
    this.root.setLeft(this.setupLeft());
    this.root.setCenter(this.setupCenter());

    stage.setTitle("To-Do List App");
    stage.setMinWidth(300);
    stage.setMinHeight(200);
    stage.setOnCloseRequest(e -> this.controller.quit(e));

    Scene scene = new Scene(this.root, 600, 400, Color.WHITE);
    stage.setScene(scene);
    stage.show();

    // Disable the center pane on first run
    this.disableCenterPane();

    this.root.requestFocus();
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
    menuItem2.setOnAction(e -> this.deleteReminderAction());

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

    MenuItem editEndDate = new MenuItem("Edit end date");
    editEndDate.setOnAction(e -> this.controller.doSetNewEndDate(this.dateLabel));

    MenuItem menuItem4 = new MenuItem("Preferences");

    editMenu.getItems().addAll(menuItem1, menuItem2, menuItem3, editEndDate, separator1, menuItem4);
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
    vBox.setPrefWidth(150);
    VBox.setVgrow(this.taskTableView, Priority.ALWAYS);

    this.setupLeftTopTable();
    vBox.getChildren().addAll(this.taskTableView, this.setupBottomLeftButtons());
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

    TableColumn<Task, BooleanProperty> statusColumn = new TableColumn<>("Done");
    statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    statusColumn.setCellFactory(col -> new CheckBoxTableCell<>(index -> {
      BooleanProperty active = new SimpleBooleanProperty(this.taskTableView.getItems().get(index).isStatus());
      active.addListener((obs, wasActive, isNowActive) -> {
        Task task = this.taskTableView.getItems().get(index);
        task.setStatus(isNowActive);
      });
      return active;
    }));
    statusColumn.setMinWidth(34);
    statusColumn.setMaxWidth(34);


    ContextMenu contextMenu = this.setupTableContextMenu();

    // Set context menu on row, but use a binding to make it only show for non-empty rows
    this.taskTableView.setRowFactory(tableView -> {
      TableRow<Task> row = new TableRow<>();
      row.contextMenuProperty().bind(
              Bindings.when(row.emptyProperty())
                      .then((ContextMenu) null)
                      .otherwise(contextMenu)
      );
      return row;
    });

    // set on left click action
    this.taskTableView.setOnMouseClicked((MouseEvent event) -> {
      Task task = this.taskTableView.getSelectionModel().getSelectedItem();

      if ((event.getButton().equals(MouseButton.PRIMARY) || event.getButton().equals(MouseButton.SECONDARY))
              && task != null) {
        this.controller.displayTask(task, this.taskTitleTextField,
                this.htmlEditor, this.dueDateButton, this.dateLabel);

        this.enableCenterPane();
        this.refreshTable();

        if (event.getButton().equals(MouseButton.PRIMARY)) {
          contextMenu.hide();
        }
      }
    });

    // Make the table editable to allow the user to directly change the Task active status
    this.taskTableView.setEditable(true);

    this.taskTableView.setItems(this.controller.getTaskListWrapper());
    this.taskTableView.getColumns().addAll(Arrays.asList(titleColumn, statusColumn));
    //Set a default sort column
    this.taskTableView.getSortOrder().add(titleColumn);
  }

  /**
   * Sets up a ContextMenu containing the MenuItems for the left TableView
   *
   * @return an already set-up ContextMenu for the left TableView
   */
  private ContextMenu setupTableContextMenu() {
    ContextMenu contextMenu = new ContextMenu();

    MenuItem deleteTaskMenu = new MenuItem("Delete task");
    deleteTaskMenu.setOnAction(e -> this.deleteReminderAction());

    SeparatorMenuItem separator = new SeparatorMenuItem();

    MenuItem setDueDateMenu = new MenuItem("Set due date");
    setDueDateMenu.setOnAction(e -> this.controller.doSetNewEndDate(this.dateLabel));

    contextMenu.getItems().addAll(setDueDateMenu, separator, deleteTaskMenu);
    return contextMenu;
  }

  /**
   * Sets up a Vbox containing the bottom left buttons.
   *
   * @return an already set-up Vbox containing the bottom left buttons
   */
  private VBox setupBottomLeftButtons() {
    VBox buttonBox = new VBox();

    Button newReminderButton = new Button("New Reminder");
    newReminderButton.setTooltip(new Tooltip("Create a New Reminder"));
    newReminderButton.setOnAction(e -> {
      this.controller.showNewReminderDialog();
      this.refreshTable();
    });
    newReminderButton.setPrefWidth(150);
    newReminderButton.setAlignment(Pos.CENTER);

    ImageView plusIcon = this.imageLoader.getImage("plus-icon");
    if (plusIcon != null) {
      plusIcon.setFitHeight(12);
      newReminderButton.setGraphic(plusIcon);
    }


    Button deleteReminderButton = new Button("Delete Reminder");
    deleteReminderButton.setTooltip(new Tooltip("Delete Selected Reminder"));
    deleteReminderButton.setPrefWidth(150);
    deleteReminderButton.setAlignment(Pos.CENTER);
    deleteReminderButton.setOnAction(e -> this.deleteReminderAction());

    ImageView trashIcon = this.imageLoader.getImage("trash-icon");
    if (trashIcon != null) {
      trashIcon.setFitHeight(12);
      deleteReminderButton.setGraphic(trashIcon);
    }

    buttonBox.getChildren().addAll(newReminderButton, deleteReminderButton);
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
    this.taskTitleTextField.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, Event::consume);

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
    ImageView zoomOutIcon = this.imageLoader.getImage("zoom-out");
    if (zoomOutIcon != null) {
      zoomOutIcon.setFitHeight(10);
      zoomOutButton.setGraphic(zoomOutIcon);
    }

    Button zoomInButton = new Button("Zoom in");
    zoomInButton.setOnAction(e -> this.zoomInAction());
    ImageView zoomInIcon = this.imageLoader.getImage("zoom-in");
    if (zoomOutIcon != null) {
      zoomInIcon.setFitHeight(10);
      zoomInButton.setGraphic(zoomInIcon);
    }

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
   * Performs the delete Task action
   */
  private void deleteReminderAction() {
    boolean taskDeleted = this.controller.doDeleteReminder();
    if (taskDeleted) {
      this.disableCenterPane();
      this.refreshTable();
    }
  }

  /**
   * Clears the text of the given Control.
   *
   * @param control The Control item to clear the text from, can be a Button, TextArea, TextField or Label
   */
  private void clearControlText(Control control) {
    if (control != null) {
      if (control instanceof Labeled) {
        ((Labeled) control).setText("");
      }
      if (control instanceof TextInputControl) {
        ((TextInputControl) control).clear();
      }
      if (control instanceof HTMLEditor) {
        ((HTMLEditor) control).setHtmlText("");
      }
    }
  }

  /**
   * Disables and clears the text of center pane's TextFields, Buttons and Labels.
   */
  private void disableCenterPane() {
    this.clearControlText(this.taskTitleTextField);
    this.clearControlText(this.htmlEditor);
    this.clearControlText(this.dueDateButton);
    this.clearControlText(this.dateLabel);

    this.root.getCenter().setDisable(true);
  }

  /**
   * Enables the center pane.
   */
  private void enableCenterPane() {
    this.root.getCenter().setDisable(false);
  }

  /**
   * Refreshes the table and forces a sort of the data
   */
  private void refreshTable() {
    this.taskTableView.refresh();
    this.taskTableView.sort();
  }
}
