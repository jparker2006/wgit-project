import java.util.*;
import java.io.*;

public class Git {
    ArrayList<String> aEntries;
    public Git() {
        aEntries = new ArrayList<>();
    }

    public void initialize() throws Exception {
        File f_objects = new File("./objects");
        if (!f_objects.exists())
            f_objects.mkdirs();
        File f_index = new File("index");
        if (!f_index.exists())
            f_index.createNewFile();
    }

    public void addBlob(String sFilePath) throws Exception {
        Blob blob = new Blob(sFilePath);
        File file = new File(sFilePath);
        String sEntry = "blob : " + blob.getHash() + " : " + file.getName();
         if (!this.aEntries.contains(sEntry)) 
            this.aEntries.add(sEntry);
        writeToIndex();
    }

    public void addDirectory(String sDirectory) throws Exception {
        Tree t = new Tree();
        String sTree = t.addDirectory(sDirectory);
        String entry = "tree : " + sTree + " : " + sDirectory;
        if (!this.aEntries.contains(entry)) 
            this.aEntries.add(entry);
        writeToIndex(); 
    }

    public void clearIndex() throws Exception {
        Utils.writeFile("index", "");
        this.aEntries = new ArrayList<String>();
    }

    public void writeToIndex() throws Exception {
        String sIndex = "";
        for (int i=0; i<this.aEntries.size(); i++) {
            sIndex += aEntries.get(i) + "\n";
        }
        Utils.writeFile("index", sIndex.trim());
    }

    public void deleteFile(String sPath) throws Exception {
        this.aEntries.add("*deleted* " + sPath);
    }

    public void editFile(String sPath) throws Exception {
        this.aEntries.add("*edited* " + sPath);
    }
}
