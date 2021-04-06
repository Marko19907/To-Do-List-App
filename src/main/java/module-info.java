module project.toDoListApp {
  requires javafx.controls;
  requires java.logging;
  requires org.json;
  exports project.toDoListApp.view;

  opens project.toDoListApp to javafx.base;
}