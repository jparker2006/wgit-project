import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BlobTest {
    static File f;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        File f = new File ("a.txt");
        f.createNewFile();
        FileWriter fw = new FileWriter (f);
        fw.write("a");
        fw.close();
    }
    @Test
    void testConstructor() throws Exception{
        Blob b = new Blob ("a.txt");
        File f = new File ("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8");
        assertTrue(f.exists());
    }
    @Test
    void testGetHash() throws Exception {
        Blob b = new Blob ("a.txt");
        assertEquals("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8", b.getHash());
    }

    @Test
    void testHashString() throws Exception {
        assertEquals("86f7e437faa5a7fce15d1ddcb9eaeaea377667b8", Blob.hashString("a"));
    }
}
