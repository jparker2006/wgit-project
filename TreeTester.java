import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.LinkedHashMap;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import apple.laf.JRSUIUtils.Tree;

public class TreeTester {
    private static String sFile = "input.txt";
    @Test
    @DisplayName("Testing add")
    void TreeAdd() throws Exception {
        tree tree = new tree();
        String t1 = "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b";
        String t2 = "blob : 640ab2bae07bedc4c163f679a746f7ab7fb5d1fa : input.txt";
        tree.add(t1);
        tree.add(t2);
        assertTrue("Add is not working", tree.getContents().contains(t1) && tree.getContents().contains(t2));
    }

    @Test
    void TreeRemove() throws Exception {
        tree tree = new tree();
        String t1 = "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b";
        String t2 = "blob : 640ab2bae07bedc4c163f679a746f7ab7fb5d1fa : input.txt";
        tree.add(t1);
        tree.add(t2);
        tree.remove("input.txt");
        assertFalse ("Did not remove file", tree.getContents().contains(t2));
        tree.remove("640ab2bae07bedc4c163f679a746f7ab7fb5d1fa");
        assertFalse ("Did not remove tree ", tree.getContents().contains(t1));
    }

    @Test
    void TreeWriteToFile() throws Exception {
        tree tree = new tree();
        tree.add("blob : 640ab2bae07bedc4c163f679a746f7ab7fb5d1fa : input.txt");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        String hash = Blob.hashString(tree.getContents());
        File f_tree = new File ("./objects/" + hash);
        assertTrue ("Tree does not write to correct SHA file", f_tree.exists());
        // assertTrue ("Tree Sha file does not contain the correct information", Utils.readFileToString("./objects/" + hash).equals(tree.getContents()));
    }

    @Test
    void TreeTestAddDirectory() throws Exception {
        File subDir = new File("./test1");
        subDir.mkdirs();
        FileWriter fw = new FileWriter("./test1/examplefile1.txt");
        fw.write("This is example file 1");
        fw.close();
        fw = new FileWriter("./test1/examplefile2.txt");
        fw.write("This is example file 2");
        fw.close(); 
        fw = new FileWriter("./test1/examplefile3.txt");
        fw.write("This is example file 3");
        fw.close(); 
        tree t = new tree();
        t.addDirectory("./test1");
        String hash = t.getHash();
        String contents = readFromFile("./objects/" + hash);
        assertTrue ("tree is missing expected files", contents.contains("./test1/examplefile1.txt"));
    }

    @Test
    void TreeTestAdvancedAddDirectory() throws Exception {
        File subDir = new File("./folder");
        subDir.mkdir();
        subDir = new File ("./folder/test2");
        subDir.mkdir();
        subDir = new File ("./folder/test3");
        subDir.mkdir();
        FileWriter fw = new FileWriter("./folder/examplefile1.txt");
        fw.write("This is example file 1");
        fw.close();
        fw = new FileWriter("./folder/examplefile2.txt");
        fw.write("This is example file 2");
        fw.close(); 
        fw = new FileWriter("./folder/examplefile3.txt");
        fw.write("This is example file 1");
        fw.close();
        fw = new FileWriter ("./folder/test3/tester.txt");
        fw.write("Tester");
        fw.close();
        tree t = new tree();
        t.addDirectory("./folder");
        String hash = t.getHash();
        String contents = readFromFile("./objects/" + hash);
        assertTrue("Tree doesnt have the files", contents.contains("./folder/examplefile1.txt"));
    }

    public static String readFromFile (String file) throws IOException {
        BufferedReader br = new BufferedReader (new FileReader (file));
        StringBuilder sb = new StringBuilder();
        while (br.ready()) {
            sb.append((char) br.read());
        }
        br.close();
        return sb.toString(); 
    }
}