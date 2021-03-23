module project.toDoListApp {
    requires javafx.controls;
    exports project.toDoListApp.view;
    opens project.toDoListApp to javafx.base;
}