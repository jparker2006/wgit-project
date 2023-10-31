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
        else if (args.length == 1) { // edit or deletion
            System.out.println("Here");
            map.put("CHANGE_KEY", sEntry);
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
    
    public String addDirectory(String sDirectoryPath) throws Exception {
        Tree childTree = new Tree();
        File f_directory = new File(sDirectoryPath);
        if (!f_directory.isDirectory())
            throw new Exception("Invalid pathing");
        File[] af_FileLS = f_directory.listFiles();
        for (File f: af_FileLS) {
            if (f.isFile()) {
                String filePath = sDirectoryPath + "/" + f.getName();
                Blob b = new Blob(filePath);
                childTree.add("blob : " + b.getHash() + " : " + f.getName());
            }
            else if (f.isDirectory()) { 
                Tree recursiveChildTree = new Tree();
                String sTempPath = f.getPath();
                recursiveChildTree.addDirectory(sTempPath);
                recursiveChildTree.toFile();
                add("tree : " + Utils.hashString(recursiveChildTree.stringify()) + " : " + sTempPath);
            }
        }
        childTree.toFile();
        String sChildHash = Utils.hashString(childTree.stringify());
        add("tree : " + sChildHash + " : " + sDirectoryPath);
        return sChildHash;
    }

    public void delete(String sPath, String sParentHash) throws Exception {
        String sFile = Utils.readFile("objects/" + sParentHash);
        String sParentsIndexTree = sFile.split("\n")[0];
        String sParentsTreeContents = Utils.readFile("objects/" + sParentsIndexTree);
        String[] aParentsPaths = sParentsTreeContents.split("\n");
        for (int i=0; i<aParentsPaths.length; i++) {
            String sType = Utils.getFirstWordOfString(aParentsPaths[i]);
            if (sType.equals("blob")) {
                String sFileName = Utils.getLastWordOfString(aParentsPaths[i]);
                if (sFileName.equals(sPath)) {
                    Utils.removeLineInFile("objects/" + sParentsIndexTree, i);
                    add("*deleted* " + sPath);
                    return;
                }
            }
            else if (sType.equals("tree")) {
                // search directory
            }
        }
        String sGrandparentsHash = sFile.split("\n")[1];
        if (sGrandparentsHash.isEmpty()) {
            return;
        }
        delete(sPath, sGrandparentsHash);
    }

    public void edit(String sPath) {
    }
}