package project.toDoListApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import project.toDoListApp.utility.FileUtility;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskSaveTest {

    @Test
    @DisplayName("Test the task save function")
    void testSaveTask()
    {
        //Change path and/or user after what is locally on your computer
        String path = "C:/Users/user/Desktop/";
        FileUtility fileUtility = new FileUtility();
        Task task = new Task(
                "Check test",
                "check test task to find issues in the program",
                "important",
                LocalDate.MAX
        );
        fileUtility.writeTaskToJson(task,path);

        assertEquals("Check test",task.getTaskName());
        assertTrue(fileUtility.taskExists(path, task));
    }
}
