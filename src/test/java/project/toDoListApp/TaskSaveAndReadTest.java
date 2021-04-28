package project.toDoListApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.toDoListApp.utility.FileUtility;

import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskSaveAndReadTest {

    @Test
    @DisplayName("Test the task save function")
    void testSaveTask() throws IOException, ClassNotFoundException {
        //Change path and/or user after what is locally on your computer
        FileUtility fileUtility = new FileUtility();
        Task task = new Task(
                "Check test",
                "check test task to find issues in the program",
                "important",
                LocalDate.MAX
        );
        TaskRegister register = new TaskRegister();
        register.addTask(task);
        register.addTask(new Task("test","test","test",LocalDate.MAX));
        fileUtility.saveToFile("tasks/registerexample.txt",register);

        assertEquals(((TaskRegister) fileUtility.readFromFile("tasks/registerexample.txt")).getAllTasks().toString(),register.getAllTasks().toString());
    }
}
