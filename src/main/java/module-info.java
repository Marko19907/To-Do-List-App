module project.toDoListApp {
    requires javafx.controls;
    exports project.toDoListApp.view;
    exports project.toDoListApp;

    opens project.toDoListApp to javafx.base;
}