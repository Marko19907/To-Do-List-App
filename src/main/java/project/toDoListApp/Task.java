package project.toDoListApp;

import java.time.LocalDate;

/**
 * Class Task represents a single task in the To-do list application
 * It stores a task name, description and category, as well as the due date,
 * status and the date on which the task was created
 */
public class Task {
    private String taskName;
    private String description;
    private String category;
    private LocalDate dueDate;
    private boolean status;

    private final LocalDate dateAdded;

    /**
     * Constructor for task objects
     */
    public Task(String taskName, String description, String category, LocalDate dueDate) {
        if (taskName == null || description == null || category == null || dueDate == null) {
            throw new IllegalArgumentException("taskName, description, category or dueDate can not be null!");
        }

        this.taskName = taskName;
        this.description = description;
        this.category = category;
        this.dueDate = dueDate;

        this.status = false;
        this.dateAdded = LocalDate.now();
    }

    /**
     * Returns the task name (title)
     *
     * @return The task name as a String
     */
    public String getTaskName() {
        return this.taskName;
    }

    /**
     * Sets the given taskName
     *
     * @param taskName The task name to set,
     *                 can not be blank or null
     */
    public void setTaskName(String taskName) {
        if (taskName != null) {
            if (!taskName.isBlank()) {
                this.taskName = taskName;
            }
        }
    }

    /**
     * Returns the task description
     *
     * @return The task description as a String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the given description
     *
     * @param description The description to set,
     *                    can not be null
     */
    public void setDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    /**
     * Returns the category
     *
     * @return Returns the category as a String
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets the given String as the task's category
     *
     * @param category The category to set,
     *                 can not be blank or null
     */
    public void setCategory(String category) {
        if (category != null) {
            if (!category.isBlank()) {
                this.category = category;
            }
        }
    }

    /**
     * Returns the due date of the task
     *
     * @return The due date of the task as a LocalDate
     */
    public LocalDate getDueDate() {
        return this.dueDate;
    }

    /**
     * Sets the due date of the task
     *
     * @param dueDate The due date to set,
     *                can not be null or before the date the task was originally created
     */
    public void setDueDate(LocalDate dueDate) {
        if (dueDate != null) {
            if (dueDate.isAfter(this.getDateAdded())) {
                this.dueDate = dueDate;
            }
        }
    }

    /**
     * Returns the task status
     *
     * @return True if the task is complete,
     * false otherwise
     */
    public boolean isStatus() {
        return this.status;
    }

    /**
     * Sets the status of the task
     *
     * @param status The status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Returns the task creation date
     *
     * @return The task creation date as a LocalDate
     */
    public LocalDate getDateAdded() {
        return this.dateAdded;
    }
}
