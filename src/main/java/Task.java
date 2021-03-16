import java.time.LocalDate;

public class Task
{
    private String taskName;
    private String description;
    private String category;
    private LocalDate dueDate;
    private boolean status;

    private final LocalDate dateAdded;

    /**
     * Constructor for task objects
     */
    public Task()
    {
        this.dateAdded = LocalDate.now();
    }
}
