import java.io.File;
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;



public class tree{
    private Map <String, String> map;
    private String sha1;
    public tree () throws IOException
    {
        this.map = new LinkedHashMap<>();
        // File tree = new File("tree");
        // tree.createNewFile(); 
    }
    public Map <String, String> getMap() {
        return map;
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
        int count = map.size ();
        int curr = 0;
        for (String v : map.values())
        {
            s.append(v);
            curr++;
            if (curr < count)
            {
                s.append("\n");
            }
        }
        sha1 = Blob.hashString(s.toString());
        String fileName = "./objects/" + sha1;
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (String entry : map.values()) {
                writer.println(entry);
            }
        }
    }
    public String returnHash()
    {
        return sha1;
    }
    public static void main(String[] args) throws IOException {
        tree ex = new tree();
        
        ex.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        ex.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        ex.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83 : file3.txt");
        ex.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        ex.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        // Save tree data to a file in the 'objects' folder
        ex.toFile();
    }
}