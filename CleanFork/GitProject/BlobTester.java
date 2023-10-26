import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BlobTester {
    @BeforeAll
    static void beforeAll() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.writeFile("Test.txt", "This is file contents.");
        Blob blob = new Blob("Test.txt");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteFile("Test.txt");
        Utils.deleteDirectory("objects");
    }

    @Test
    void testConstructor() throws Exception {
        assertTrue(Utils.exists("objects/65b8e639498af65675883d6cd6ab3c0aaf984f1a"));
    }

    @Test
    @DisplayName("Test that hash is the correct SHA1.")
    void testHashString() throws Exception {
        String saPredictedHash = "86f7e437faa5a7fce15d1ddcb9eaeaea377667b8";
        assertEquals(saPredictedHash, Utils.hashString("a"));
    }
}
