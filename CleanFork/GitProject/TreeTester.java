import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TreeTester {
    @BeforeAll
    static void beforeAll() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");

        Utils.writeFile("Test1.txt", "A"); // 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b
        Utils.writeFile("Test2.txt", "B"); // ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec
        Utils.writeFile("Test3.txt", "C"); // 32096c2e0eff33d844ee6d675407ace18289357d

        Git git = new Git();
        git.initialize();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");

        Utils.deleteFile("Test1.txt");
        Utils.deleteFile("Test2.txt");
        Utils.deleteFile("Test3.txt");
    }

    @Test
    @DisplayName("Test that tree can add a file.")
    void testAdd() throws Exception {
        Tree tree = new Tree();

        // Incorrect format
        assertThrows(Exception.class, () -> {
            tree.add("Foobar");
        });

        // No file name for blob
        assertThrows(Exception.class, () -> {
            tree.add("blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b");
        });

        tree.add("blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : Test1.txt");
        tree.add("blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : Test2.txt");
        tree.add("blob : 32096c2e0eff33d844ee6d675407ace18289357d : Test3.txt");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");

        // Dupes. Shouldn't do anything
        tree.add("blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : Test1.txt");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");

        LinkedHashMap<String, String> hm = tree.getHM();
        assertTrue(hm.containsKey("Test1.txt"));
        assertTrue(hm.containsKey("Test2.txt"));
        assertTrue(hm.containsKey("Test3.txt"));
        assertTrue(hm.containsKey("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b"));
        assertTrue(hm.containsKey("e7d79898d3342fd15daf6ec36f4cb095b52fd976"));
    }

    @Test
    @DisplayName("Test that tree can remove a file.")
    void testRemove() throws Exception {
        Tree tree = new Tree();

        tree.add("blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : Test1.txt");
        tree.add("blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : Test2.txt");
        tree.add("blob : 32096c2e0eff33d844ee6d675407ace18289357d : Test3.txt");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");

        tree.remove("Test1.txt");
        tree.remove("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");

        LinkedHashMap<String, String> hm = tree.getHM();
        assertTrue(!hm.containsKey("Test1.txt"));
        assertTrue(hm.containsKey("Test2.txt"));
        assertTrue(hm.containsKey("Test3.txt"));
        assertTrue(!hm.containsKey("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b"));
        assertTrue(hm.containsKey("e7d79898d3342fd15daf6ec36f4cb095b52fd976"));
    }

    @Test
    @DisplayName("Test to write the tree.")
    void testToFile() throws Exception {
        Tree tree = new Tree();
        tree.add("blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : Test1.txt");
        tree.add("blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : Test2.txt");
        tree.add("blob : 32096c2e0eff33d844ee6d675407ace18289357d : Test3.txt");
        tree.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        tree.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        tree.toFile();
        String sExpectedHashOfTree = "302a128fd767f7d86c8feb521e8247e6645226c9";

        assertTrue(Utils.exists("objects/" + sExpectedHashOfTree));
        assertEquals(
            Utils.readFile("objects/" + sExpectedHashOfTree),
            "blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : Test1.txt" + "\n" +
            "blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : Test2.txt" + "\n" +
            "blob : 32096c2e0eff33d844ee6d675407ace18289357d : Test3.txt" + "\n" +
            "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b" + "\n" +
            "tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976"
        );
    }

    @Test
    @DisplayName("Test add directory basic.")
    void testAddDirectoryBasicExample() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteDirectory("dir");

        Utils.writeFile("dir/file1.txt", "A");
        Utils.writeFile("dir/file2.txt", "B");
        Utils.writeFile("dir/file3.txt", "C");

        Tree tree = new Tree();
        tree.addDirectory("dir");
        tree.toFile();

        String contents = Utils.readFile("objects/" + Utils.hashString(tree.stringify()));
        assertTrue(contents.contains("dir/file1.txt"));
        assertTrue(contents.contains("6dcd4ce23d88e2ee9568ba546c007c63d9131c1b"));
    }

    @Test
    @DisplayName("Test add directory advanced.")
    void testAddDirectoryAdvanced () throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteDirectory("dir");

        Utils.writeFile("dir/file1.txt", "A");
        Utils.writeFile("dir/file2.txt", "B");
        Utils.writeFile("dir/file3.txt", "C");

        Utils.createFolder("dir/subdir1");
        Utils.writeFile("dir/subdir2/file4.txt", "D");

        Tree tree = new Tree();
        tree.addDirectory("dir");
        tree.toFile();

        String contents = Utils.readFile("objects/" + Utils.hashString(tree.stringify()));
        assertTrue(contents.contains("dir/file1.txt"));
        assertTrue(contents.contains("dir/subdir1"));
        assertTrue(contents.contains("be0974336796cee8e4e51fcc1d02d2c6583d9181"));
    } 
}
