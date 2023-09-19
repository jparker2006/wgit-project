import static org.junit.Assert.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExampleTester {
private static tree ex;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        /*
         * Utils.writeStringToFile("junit_example_file_data.txt", "test file contents");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */
        ex = new tree();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        /*
         * Utils.deleteFile("junit_example_file_data.txt");
         * Utils.deleteFile("index");
         * Utils.deleteDirectory("objects");
         */
    }

    @Test
    @DisplayName("[8] Test if initialize and objects are created correctly")
    void testInitialize() throws Exception {

        Git g = new Git ();
        g.initialize();

        // check if the file exists
        File file = new File("index");
        Path path = Paths.get("objects");

        assertTrue(file.exists());
        assertTrue(Files.exists(path));
    }
    @Test
    void testAdd() throws Exception {
        ex.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        ex.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        ex.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83 : file3.txt");
        ex.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        ex.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        assertEquals(5, ex.getMap().size());
    }
    @Test
    void testRemove() throws Exception {
        ex.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        ex.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        ex.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83 : file3.txt");
        ex.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        ex.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        // ex.remove("81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
        ex.remove("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        assertNull(ex.getMap().get("bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b"));
        ex.remove("file2.txt");
        ex.remove("file1.txt");
        assertEquals(2, ex.getMap().size());
    }
    @Test
    void testToFile() throws Exception {
        ex.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f : file1.txt");
        ex.add("blob : 01d82591292494afd1602d175e165f94992f6f5f : file2.txt");
        ex.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83 : file3.txt");
        ex.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
        ex.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
        ex.toFile();
    }
    @Test
    @DisplayName("[15] Test if adding a blob works.  5 for sha, 5 for file contents, 5 for correct location")
    void testCreateBlob() throws Exception {

        try {

            // Manually create the files and folders before the 'testAddFile'
            // MyGitProject myGitClassInstance = new MyGitProject();
            // myGitClassInstance.init();

            // TestHelper.runTestSuiteMethods("testCreateBlob", file1.getName());

        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Check blob exists in the objects folder
        // File file_junit1 = new File("objects/" + file1.methodToGetSha1());
        // assertTrue("Blob file to add not found", file_junit1.exists());

        // // Read file contents
        // String indexFileContents = MyUtilityClass.readAFileToAString("objects/" + file1.methodToGetSha1());
        // assertEquals("File contents of Blob don't match file contents pre-blob creation", indexFileContents,
        //         file1.getContents());
    }
}
