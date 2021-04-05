module project.toDoListApp {
  requires javafx.controls;
  requires java.logging;
  exports project.toDoListApp.view;

  opens project.toDoListApp to javafx.base;
}