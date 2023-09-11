import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class tree{
    public tree () throws IOException
    {
        File tree = new File("tree");
        tree.createNewFile(); 
    }
    public void addEntry (String entry) throws IOException
    {
        FileWriter fw = new FileWriter ("tree",true);
        fw.write(entry);
        fw.close();
    }
}