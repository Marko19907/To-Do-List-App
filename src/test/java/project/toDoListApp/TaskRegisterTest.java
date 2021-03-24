package project.toDoListApp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskRegisterTest
{
    @Test
    @DisplayName("Test adding tasks to the task register")
    void testAddingTasks()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);

        taskRegister.addTask(task1);
        taskRegister.addTask(task2);

        assertEquals(2, taskRegister.getNumberOfTasks());
    }

    @Test
    @DisplayName("Test adding null to the task register")
    void testAddingNull()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);

        taskRegister.addTask(task1);
        taskRegister.addTask(task2);
        taskRegister.addTask(null);

        assertEquals(2, taskRegister.getNumberOfTasks());
    }

    @Test
    @DisplayName("Test getting a List of tasks from the task register")
    void testGettingAllTasksList()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);
        HashSet<Task> taskList = new HashSet<>();
        taskList.add(task1);
        taskList.add(task2);

        taskRegister.addTask(task1);
        taskRegister.addTask(task2);

        assertEquals(2, taskRegister.getNumberOfTasks());
        assertEquals(taskList, new HashSet<>(taskRegister.getAllTasks()));
    }

    @Test
    @DisplayName("Test removing tasks from the task register")
    void testRemovingTasks()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);
        taskRegister.addTask(task1);
        taskRegister.addTask(task2);
        assertEquals(2, taskRegister.getNumberOfTasks());

        assertTrue(taskRegister.removeTask(task2));

        assertEquals(1, taskRegister.getNumberOfTasks());
    }

    @Test
    @DisplayName("Test removing null from the task register")
    void testRemovingNull()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);
        taskRegister.addTask(task1);
        taskRegister.addTask(task2);
        assertEquals(2, taskRegister.getNumberOfTasks());

        assertFalse(taskRegister.removeTask(null));

        assertEquals(2, taskRegister.getNumberOfTasks());
    }

    @Test
    @DisplayName("Test removing non existent task from the task register")
    void testRemovingNonExistentTasks()
    {
        LocalDate endDate = LocalDate.parse("3100-12-01");
        TaskRegister taskRegister = new TaskRegister();
        Task task1 = new Task("Test title", "Test description",
                "None", endDate);
        Task task2 = new Task("Test title 2", "Test description 2",
                "None 2", endDate);
        taskRegister.addTask(task1);
        assertEquals(1, taskRegister.getNumberOfTasks());

        assertFalse(taskRegister.removeTask(task2));

        assertEquals(1, taskRegister.getNumberOfTasks());
    }
}