import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class Tree {
    private LinkedHashMap<String, String> map;

    public Tree() throws IOException {
        this.map = new LinkedHashMap<>();
    }

    public void add(String sEntry) throws Exception {
        String[] args = sEntry.split(" : ");
        if (args[0].equals("tree")) {
            if (map.containsKey(args[1])) return;
            map.put(args[1], sEntry);
        }
        else if (!map.containsKey(args[2])) // blob
            map.put(args[2], sEntry);
    }

    public void remove (String sEntry) throws Exception {
        map.remove(sEntry);
    }

    public void toFile() throws Exception {
        String sFile = stringify();
        String sHashOfTree = Utils.hashString(sFile);
        Utils.writeFile("./objects/" + sHashOfTree, sFile);
    }

    public String stringify() {
        StringBuilder s = new StringBuilder();
        for (String sEntry: this.map.values()) {
            s.append(sEntry);
            s.append("\n");
        }
        return s.toString().trim();
    }

    public LinkedHashMap<String, String> getHM() {
        return this.map;
    }
    
    public void addDirectory(String sDirectoryPath) throws Exception {
        File f_directory = new File(sDirectoryPath);
        if (!f_directory.isDirectory())
            throw new Exception("Invalid pathing");
        File[] af_FileLS = f_directory.listFiles();
        for (File f: af_FileLS) {
            if (f.isFile()) {
                String filePath = sDirectoryPath + "/" + f.getName();
                Blob b = new Blob (filePath);
                add("blob : " + b.getHash() + " : " + filePath);
            }
            else if (f.isDirectory()) { 
                Tree childTree = new Tree();
                String sTempPath = f.getPath();
                childTree.addDirectory(sTempPath);
                childTree.toFile();
                add("tree : " + Utils.hashString(childTree.stringify()) + " : " + sTempPath);
            }
        }
    }
}