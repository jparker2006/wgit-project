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
/*
    public void addDirectory(String sDirectoryPath) throws Exception {
        File f_directory = new File(sDirectoryPath);
        if (!f_directory.isDirectory())
            throw new Exception("Invalid pathing");
        File[] af_FileLS = f_directory.listFiles();
        for (File f: af_FileLS) {
            if (f.isDirectory()) {
                tree t = new tree();
                String sPath = f.getPath();
                t.addDirectory(sPath);
                String sTreeHash = t.getHash();
                this.add("tree : " + sTreeHash + " : " + sPath);
            }
            else {
                String sFilePath = sDirectoryPath + "/" + f.getName();
                Blob blob = new Blob(sFilePath);
                this.add("blob : " + blob.getHash() + " : " + sFilePath);
            }
        }
    }


    */
}