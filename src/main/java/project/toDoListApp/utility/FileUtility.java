package project.toDoListApp.utility;

import java.io.*;
import java.util.logging.Logger;


public class FileUtility {

    //Initialize the logger for easier access.
    private final Logger logger;

    public FileUtility(){
        this.logger = Logger.getLogger(this.getClass().toString());
    }

    /**
     * Save a binary version of the object to the given file.
     * If the file name is not an absolute path, then it is assumed
     * to be relative to the current project folder.
     * @param destinationFile The file where the details are to be saved.
     * @param object The object to be saved in binary version.
     * @throws IOException If the saving process fails for any reason.
     */
    public void saveToFile(String destinationFile, Object object) throws IOException{
        File dir = new File("tasks");
        if(!dir.exists()){
            dir.mkdir();
        }
        File outputFile = new File(destinationFile);
        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(outputFile));
        os.writeObject(object);
        os.close();
    }

    /**
     * Read the binary version of an address book from the given file.
     * If the file name is not an absolute path, then it is assumed
     * to be relative to the current project folder.
     * @param sourceFile The file from where the details are to be read.
     * @return The Task object.
     * @throws IOException If the reading process fails for any reason.
     */
    public Object readFromFile(String sourceFile) throws IOException, ClassNotFoundException{
        try{
            FileInputStream fi = new FileInputStream(sourceFile);
            ObjectInputStream oi = new ObjectInputStream(fi);

            Object obj = oi.readObject();

            oi.close();
            fi.close();

            return obj;
        }catch (IOException e){
            throw new IOException("Unable to make a valid filename for "+
                    sourceFile);
        }
    }

}
