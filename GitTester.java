import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GitTester {
    @BeforeAll
    static void beforeAll() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");
        Utils.writeFile("Test.txt", "This is file contents.");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteFile("Test.txt");
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");
    }

    @Test
    @DisplayName("Test that index initialized index & objects.")
    void testInitialize() throws Exception {
        Git index = new Git();
        index.initialize();
        assertTrue(Utils.exists("index"));
        assertTrue(Utils.exists("objects"));
    }

    @Test
    @DisplayName("Test that index can add a file.")
    void testAddBlob() throws Exception {
        Git index = new Git();
        index.addBlob("Test.txt");

        String sExpectedHash = "65b8e639498af65675883d6cd6ab3c0aaf984f1a";
        assertTrue(Utils.exists("objects/" + sExpectedHash));
        assertEquals(
            Utils.readFile("index"),
            "blob : " + sExpectedHash + " : Test.txt"
        );
    }

    // @Test
    // @DisplayName("Test that index can remove a file.")
    // void testRemoveBlob() throws Exception {
    //     Git index = new Git();
    //     index.addBlob("Test.txt");
    //     Utils.writeFile("Test2.txt", "This is other file contents.");
    //     index.addBlob("Test2.txt");
    //     assertTrue(Utils.exists("objects/b39ea086fba739cadf6ad1b345f9c7e2d8c0c343"));

    //     index.deleteBlob("Test2.txt");

    //     String sExpectedHash = "65b8e639498af65675883d6cd6ab3c0aaf984f1a";
    //     assertEquals(
    //         Utils.readFile("index"),
    //         "blob : " + sExpectedHash + " : Test.txt"
    //     );
    // }
}
