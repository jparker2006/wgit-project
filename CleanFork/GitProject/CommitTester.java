import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommitTester {
    @BeforeAll
    static void beforeAll() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");
        Git index = new Git();
        index.initialize();
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");
    }

    @Test
    @DisplayName("Test that commit works.")
    void testCommit() throws Exception {
        Commit commit = new Commit("f924e482dd33576fd0de90b6376f1671b08b5f52", "Jake Parker", "This is my commit.");
        commit.commit();
        String sCommitSha = commit.getCommitSha();
        assertTrue(new File("objects/" + sCommitSha).exists());
        assertEquals(
            commit.constructTreeSha()                  + "\n" +
            "f924e482dd33576fd0de90b6376f1671b08b5f52" + "\n" +
            "\n" +
            "Jake Parker"                              + "\n" +
            Commit.getDate()                           + "\n" +
            "This is my commit.",
            Utils.readFile("objects/" + sCommitSha)
        );
    }

    @Test
    @DisplayName("Test that commits other constructor works.")
    void testCommitOtherConstructor() throws Exception {
        Commit commit = new Commit("Jake Parker", "This is my commit.");
        commit.commit();
        String sCommitSha = commit.getCommitSha();
        assertTrue(new File("objects/" + sCommitSha).exists());
        assertEquals(
            commit.constructTreeSha() + "\n" +
            ""                        + "\n" +
            "\n" +
            "Jake Parker"             + "\n" +
            Commit.getDate()          + "\n" +
            "This is my commit.",
            Utils.readFile("objects/" + sCommitSha)
        );
    }

    @Test
    @DisplayName("Test that getting the date works.")
    void testGetDate() throws Exception {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        assertEquals(Commit.getDate(), sdf.format(cal.getTime()));
    }

    // @Test
    // @DisplayName("Test that creating a commits tree works.")
    // void testCreateTree() throws Exception {
    //     Commit commit = new Commit("Jake Parker", "This is my commit.");
    //     String sTree = commit.constructTreeSha();
    //     assertTrue(Utils.exists("objects/" + sTree));
    // }
}
