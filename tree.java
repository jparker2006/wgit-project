import java.io.File;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.*;



public class tree{
    private Map <String, String> map;
    public tree () throws IOException
    {
        this.map = new HashMap<>();
        // File tree = new File("tree");
        // tree.createNewFile(); 
    }
    public void add (String entry) throws IOException
    {
        String [] seperator = entry.split (" : ");
        if (seperator.length >= 2)
        {
            String key;
            if (seperator[0].equals("tree"))
            {
                key = seperator[1];
            }
            else
            {
                key = seperator [2];
            }
            map.put(key, entry);
        }
    }
    public void remove (String entry) throws IOException
    {
        map.remove(entry);
    }
    public void toFile() throws IOException
    {
        StringBuilder s = new StringBuilder();
        for (String v : map.values())
        {
            s.append(v).append("\n");
        }
        String sha1 = Blob.hashString(s.toString());
        String fileName = "./objects/" + sha1;
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String entry : map.values()) {
                writer.println(entry);
            }
        }
    }
}