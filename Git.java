import java.util.*;
import java.io.*;

public class Git {
    protected HashMap<String, String> hm;
    public Git() {
        hm = new HashMap<String, String>();
    }

    public void initialize() throws Exception {
        File f_objects = new File("./objects");
        if (!f_objects.exists())
            f_objects.mkdirs();
        File f_index = new File("./objects/index");
        if (!f_index.exists())
            f_index.createNewFile();
    }

    public void addBlob(String sFilePath) throws Exception {
        Blob blob = new Blob(sFilePath);
        hm.put(sFilePath, blob.getHash());
        writeToIndex();
    }

    public void deleteBlob(String sFilePath) throws Exception {
        File f_delFile = new File(sFilePath);
        if (f_delFile.exists()) {
            hm.remove(sFilePath);
            writeToIndex();
        }
        else
            System.out.println("file does not exist");
    }

    public void writeToIndex() throws Exception {
        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("index")));
        for (String sFileName: hm.keySet()) {
            String sHash = hm.get(sFileName);
            writer.println(sFileName + " : " + sHash);
        }
        writer.close();
    }
}
