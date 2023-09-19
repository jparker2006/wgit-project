import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.HashMap;
import org.junit.jupiter.api.Test;

public class GitTest {
    @Test
    void testAddBlob() throws Exception {
        Git g = new Git ();
        g.initialize();
        g.addBlob ("a.txt");
        HashMap <String, String> hm = g.hm;
        assertTrue (hm.containsKey("a.txt"));
        assertNotNull(hm.get("a.txt"));
    }

    @Test
    void testDeleteBlob() throws Exception {
        Git g = new Git ();
        g.initialize();
        g.addBlob ("a.txt");
        g.deleteBlob ("a.txt");
        HashMap <String, String> hm = g.hm;
        assertFalse (hm.containsKey("a.txt"));
        assertNull(hm.get("a.txt"));
    }

    @Test
    void testInitialize() throws Exception {
        Git g = new Git ();
        g.initialize();
        assertTrue (new File ("./objects").exists());
        assertTrue (new File ("index").exists());
    }

    @Test
    void testWriteToIndex() throws Exception {
        Git g = new Git ();
        g.initialize();
        g.addBlob("a.txt");
        g.writeToIndex();
        BufferedReader reader = new BufferedReader(new FileReader("index"));
            String line;
            HashMap<String, String> hm = g.hm;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" : ");
                assertEquals(2, parts.length);
                String fileName = parts[0];
                String hash = parts[1];
                assertTrue(hm.containsKey(fileName));
                assertEquals(hm.get(fileName), hash);
            }
    }
}
