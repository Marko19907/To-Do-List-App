package project.toDoListApp.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.util.StringConverter;
import project.toDoListApp.Task;
import project.toDoListApp.TaskRegister;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Class Controller represents the main controller for the application.
 * It is responsible for handling the events from the GUI.
 */
public class Controller {
  private static final String DATE_FORMAT = "dd/MM/yyyy";
  private final TaskRegister taskRegister;
  private final ObservableList<Task> taskListWrapper;

  private Task currentTask;

  /**
   * Instantiates the controller.
   */
  public Controller() {
    this.taskRegister = new TaskRegister();
    this.taskListWrapper = FXCollections.observableArrayList(this.taskRegister.getAllTasks());

    this.fillRegisterWithTestTasks();
  }

  /**
   * Displays a given Task to the given parameters.
   *
   * @param task      The task to display, can not be null
   * @param taskTitle The TextField to set the title to, can not be null
   * @param editor    The TextArea to set the task text content to, can not be null
   * @param dueDateButton The dueDateButton to enable after a Task is displayed, can not be null
   * @param dueDateLabel The Label to set the end date text to, can not be null
   */
  public void displayTask(Task task, TextField taskTitle,
                          HTMLEditor editor, Button dueDateButton, Label dueDateLabel) {
    if (task != null && taskTitle != null && editor != null &&
        dueDateButton != null && dueDateLabel != null) {
      this.saveTaskToRegister(taskTitle, editor);

      this.currentTask = task;
      editor.setHtmlText(task.getDescription());
      taskTitle.setText(task.getTaskName());
      dueDateButton.setText("Set due date");
      dueDateLabel.setText("Due date: " + this.getLocalDateAsString(task.getDueDate()));

      this.enableControl(taskTitle);
      this.enableControl(editor);
      this.enableControl(dueDateButton);
      this.enableControl(dueDateLabel);
    }
  }

  /**
   * Saves the current task to the register after changing it's title and text content.
   *
   * @param editor    The TextArea to save the text from,
   *                  can not be null
   * @param taskTitle The TextField to get the text from,
   *                  can not be null
   */
  private void saveTaskToRegister(TextField taskTitle, HTMLEditor editor) {
    if (this.currentTask != null && taskTitle != null && editor != null) {
      if (taskTitle.getText().isBlank()) {
        //TODO: The TextField is blank, the task will throw an exception
        //Do nothing for now
      } else {
        this.currentTask.setTaskName(taskTitle.getText());
      }
      this.currentTask.setDescription(editor.getHtmlText());

      this.taskRegister.addTask(this.currentTask);
      this.updateObservableList();
    }
  }

  /**
   * Updates the observable list of tasks with fresh values from the task register.
   */
  private void updateObservableList() {
    this.taskListWrapper.setAll(this.taskRegister.getAllTasks());
  }

  /**
   * Adds a few tasks to the register for testing.
   */
  private void fillRegisterWithTestTasks() {
    this.taskRegister.addTask(new Task("Title 1", "Desc 1",
        "None", LocalDate.parse("2100-12-01")));
    this.taskRegister.addTask(new Task("Title 2", "Desc 2",
        "Cooking", LocalDate.parse("3100-12-01")));

    this.updateObservableList();
  }

  /**
   * Returns an ObservableList that holds the tasks.
   *
   * @return an ObservableList that holds the tasks
   */
  public ObservableList<Task> getTaskListWrapper() {
    return this.taskListWrapper;
  }

  /**
   * Enables the given Control.
   *
   * @param control The Control item to enable, can be a Button, TextArea, TextField or Label
   */
  private void enableControl(Control control) {
    if (control != null) {
      control.setDisable(false);
    }
  }

  /**
   * Returns a String representation of the given LocalDate.
   *
   * @param localDate The LocalDate to represent as a String, can not be null
   * @return A String representation of the given LocalDate or blank if null
   */
  private String getLocalDateAsString(LocalDate localDate) {
    // Guard condition
    if (localDate == null) {
      return "";
    }
    return localDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
  }

  /**
   * Sets the font scaling factor of the given HTMLEditor.
   *
   * @param htmlEditor The HTMLEditor to set the font scaling factor to, can not be null
   * @param scaleFactor The font scaling factor as a double, can be both positive and negative
   */
  public void doZoom(HTMLEditor htmlEditor, double scaleFactor) {
    if (htmlEditor != null) {
      WebView webView = (WebView) htmlEditor.lookup(".web-view");
      double newFontScaleFactor = webView.getFontScale() + scaleFactor;
      // round the new font scaling factor before applying it
      double newRoundedFontScale = Math.round(newFontScaleFactor * 100) / 100.0;
      if (0.5 <= newRoundedFontScale && newRoundedFontScale <= 2.0) {
        webView.setFontScale(newRoundedFontScale);
      }
    }
  }

  /**
   * Sets the new end date of the currently selected task.
   *
   * @param dateLabel The Label to set the new end date text to, can not be null
   */
  public void doSetNewEndDate(Label dateLabel) {
    if (this.currentTask == null) {
      this.showPleaseSelectItemDialog();
    } else {
      LocalDate newEndDate = this.doGetEndDateDialog();
      if (newEndDate != null && dateLabel != null) {
        this.currentTask.setDueDate(newEndDate);
        dateLabel.setText("Due date: " + this.getLocalDateAsString(newEndDate));
      }
    }
  }

  /**
   * Deletes a reminder form the list.
   *
   * @return Returns true if the current task was deleted, false otherwise
   */
  public boolean doDeleteReminder() {
    boolean success = false;
    if (this.currentTask == null) {
      this.showPleaseSelectItemDialog();
    } else {
      if (this.showDeleteConfirmationDialog()) {
        this.taskRegister.removeTask(this.currentTask);
        this.currentTask = null;
        success = true;

        this.updateObservableList();
      }
    }
    return success;
  }

  // -----------------------------------------------------------
  //    DIALOGS
  // -----------------------------------------------------------

  /**
   * Shows the About Dialog.
   */
  public void showAboutDialog() {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("About");
    alert.setHeaderText("To-Do List App");
    alert.setContentText("An application created by" + "\n"
        + "Group 2" + "\n\n"
        + "2021");

    alert.showAndWait();
  }

  /**
   * Application exit dialog. A confirmation dialog that is displayed before exiting.
   */
  public void quit(Event event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Confirm close");
    alert.setHeaderText("Exit this application?");
    alert.setContentText("Are you sure you want to exit the application?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      if (result.get() == ButtonType.OK) {
        //TODO: Save the app's state to disk before exiting?
        Platform.exit();
      } else {
        event.consume();
      }
    }
  }

  /**
   * Creates a new pop-up dialog box,
   * where the user can provide details of a new task to be added to the to-do list.
   */
  public void showNewReminderDialog() {
    //TODO: Create class that handles dialogs.
    // This method should not be handling the creation of the dialog box
    Dialog<Task> newReminderDialog = new Dialog<>();

    newReminderDialog.setTitle("Reminder Details");

    newReminderDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(20, 150, 10, 10));

    TextField taskName = new TextField();
    taskName.setPromptText("Title");

    TextField description = new TextField();
    description.setPromptText("Description");

    TextField category = new TextField();
    category.setPromptText("Category");

    TextField dueDate = new TextField();
    dueDate.setPromptText("yyyy-mm-dd");
    //TODO:Add failsafe for the date input

    grid.add(new Label("Task name:"), 0, 0);
    grid.add(taskName, 1, 0);
    grid.add(new Label("Description:"), 0, 1);
    grid.add(description, 1, 1);
    grid.add(new Label("Category:"), 0, 2);
    grid.add(category, 1, 2);
    grid.add(new Label("Due date:"), 0, 3);
    grid.add(dueDate, 1, 3);

    newReminderDialog.getDialogPane().setContent(grid);

    newReminderDialog.setResultConverter(
        (ButtonType button) -> {
          Task result = null;
          if (button == ButtonType.OK) {
            LocalDate dateDue = LocalDate.parse(dueDate.getText());
            result = new Task(taskName.getText(), description.getText(),
                category.getText(), dateDue);
          }
          return result;
        }
    );

    Optional<Task> result = newReminderDialog.showAndWait();

    if (result.isPresent()) {
      Task newTask = result.get();
      this.taskRegister.addTask(newTask);
      this.updateObservableList();
    }
  }

  /**
   * Displays a warning telling the user to select
   * an element from the list.
   */
  private void showPleaseSelectItemDialog() {
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle("Information");
    alert.setHeaderText("No items selected");
    alert.setContentText("No item is selected from the table.\n"
        + "Please select an item from the table.");

    alert.showAndWait();
  }

  /**
   * Displays a delete confirmation dialog. If the user confirms the delete,
   * <code>true</code> is returned.
   *
   * @return <code>true</code> if the user confirms the delete
   */
  public boolean showDeleteConfirmationDialog() {
    boolean deleteConfirmed = false;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Delete confirmation");
    alert.setHeaderText("Delete confirmation");
    alert.setContentText("Are you sure you want to delete this reminder?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent()) {
      deleteConfirmed = (result.get() == ButtonType.OK);
    }
    return deleteConfirmed;
  }

  /**
   * Displays a dialog in which the user can type the desired date as a String or select it from the DatePicker.
   * If the user clicks okay, a LocalDate of the desired date is returned, otherwise null.
   *
   * @return The user selected LocalDate, null if cancelled
   */
  private LocalDate doGetEndDateDialog() {
    Dialog<LocalDate> dialog = new Dialog<>();
    dialog.setTitle("Date picker");
    dialog.getDialogPane().setPrefWidth(275);

    DatePicker datePicker = this.getDatePicker();

    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);
    grid.setPadding(new Insets(5, 5, 5, 5));

    grid.add(datePicker, 0, 0);

    dialog.getDialogPane().getChildren().add(grid);
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

    dialog.setResultConverter((ButtonType button) -> {
      LocalDate result = null;
      if (button == ButtonType.OK) {
        result = datePicker.getValue();
      }
      return result;
    });

    return dialog.showAndWait().orElse(null);
  }

  /**
   * Returns a set-up DatePicker in the "dd/MM/yyyy" format and with disabled past dates.
   *
   * @return An already set-up DatePicker
   */
  private DatePicker getDatePicker() {
    DatePicker datePicker = new DatePicker();
    datePicker.setMinSize(210, 25);
    datePicker.setPromptText(DATE_FORMAT);
    datePicker.setShowWeekNumbers(true);
    datePicker.setStyle("-fx-font-size: 1.1em;");

    datePicker.setConverter(new StringConverter<>() {
      @Override
      public String toString(LocalDate date) {
        String toReturn = "";
        if (date != null) {
          try {
            toReturn = DateTimeFormatter.ofPattern(DATE_FORMAT).format(date);
          }
          catch (DateTimeException | IllegalArgumentException ignored) {
          }
        }
        return toReturn;
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate localDateToReturn = null;
        if (string != null && !string.isEmpty()) {
          try {
            localDateToReturn = LocalDate.parse(string, DateTimeFormatter.ofPattern(DATE_FORMAT));
          }
          catch (DateTimeParseException ignored) {
          }
        }
        return localDateToReturn;
      }
    });

    datePicker.setDayCellFactory(picker -> new DateCell() {
      @Override
      public void updateItem(LocalDate date, boolean empty) {
        super.updateItem(date, empty);

        this.setTooltip(new Tooltip("Select this date"));

        // Show weekends in red
        DayOfWeek day = DayOfWeek.from(date);
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
          this.setTextFill(Color.RED);
        }

        // Disable all past date and empty cells
        if (empty || date.isBefore(LocalDate.now())) {
          this.setDisable(true);
          this.setStyle("-fx-background-color: #ffe3e9;");
        }

        // Disable the current date and show it in blue
        if (date.isEqual(LocalDate.now())) {
          //this.setDisable(true);
          //TODO: Figure out if the current date is an acceptable value . . .
          this.setStyle("-fx-background-color: #e3f3ff;");
          this.setTooltip(new Tooltip("Can not be the same date as today"));
        }
      }
    });

    return datePicker;
  }
}
