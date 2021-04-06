package project.toDoListApp.utility;

import org.json.JSONObject;
import project.toDoListApp.Task;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileUtility {

    private FileWriter fileWriter;
    //Initialize the logger for easier access.
    private final Logger logger;

    public FileUtility(){
        this.logger = Logger.getLogger(this.getClass().toString());
    }

    /**
     *
     * @param jsonFilePath The path to retrieve the JSON file from,
     *                     which stores the saved task-set.
     * @return Return List<Task> from the saved JSON file.
     */
    public List<Task> retrieveTasks(String jsonFilePath){
        //TODO return all tasks in directory.
        return null;
    }

    /**
     *
     * @param taskHashSet The task set to be saved in the filesystem.
     * @param filePath The path on the os for the file to be saved in.
     */
    public void writeTaskSetToJson(HashSet<Task> taskHashSet,String filePath){
        for(Task task : taskHashSet){
            writeTaskToJson(task,filePath);
        }
    }

    /**
     *
     * @param task The task to be saved in the filesystem.
     * @param filePath The path on the os for the file to be saved in.
     */
    public void writeTaskToJson(Task task, String filePath){
        JSONObject object = new JSONObject();
        object.put("taskName",task.getTaskName());
        object.put("description",task.getDescription());
        object.put("category",task.getCategory());
        object.put("dueDate",task.getDueDate());
        try{
            fileWriter = new FileWriter(new File(filePath) + "/" + task.getTaskName() + ".json");
            fileWriter.write(object.toString());
            //logger.log(Level.INFO,"File saved as JSON - > name: " + task.getTaskName());
        }catch (Exception e){
            logger.log(Level.INFO,"Could not save JSON file by the name of " + task.getTaskName());
        }finally {
            try{
                fileWriter.flush();
                fileWriter.close();
            }catch (IOException ex){
                logger.log(Level.INFO,"Could not save JSON file by the name of (IOException)" + task.getTaskName());
            }
        }
    }

    /**
     *
     * @param dir The directory for the function to search in
     * @param task The task to be passed in the function and searched for
     *             if it exists in the path directory.
     * @return Return true if the task already exists in the
     *             directory.
     */
    public boolean taskExists(String dir,Task task){
        try{
            String[] files = new File(dir).list();
            if (files != null) {
                for(String name : files){
                    if(name.equalsIgnoreCase(task.getTaskName() + ".json")){
                        return true;
                    }
                }
            }
            return false;
        }catch (Exception e){
            logger.log(Level.INFO,"Failed to search path for file " + task.getTaskName());
        }
        return false;
    }


}
