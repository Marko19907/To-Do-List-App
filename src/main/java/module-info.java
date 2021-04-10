module project.toDoListApp {
  requires javafx.controls;
  requires javafx.web;
  requires java.logging;
  exports project.toDoListApp.view;

  opens project.toDoListApp to javafx.base;
}