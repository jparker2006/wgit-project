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
    /*
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
    // void test2Commits() throws Exception {
    //     Utils.writeFile("file1.txt", "A");
    //     Utils.writeFile("file2.txt", "B");
    //     Utils.writeFile("file3.txt", "C");
    //     Utils.writeFile("dir/file4.txt", "D");

    //     Git index2 = new Git();
    //     index2.initialize();
    //     index2.addBlob("file1.txt");
    //     index2.addBlob("file2.txt");
    //     index2.addDirectory("dir");
    //     Commit commit = new Commit("Jake Parker", "This is my commit.");
    //     commit.commit();
    //     // assertEquals(commit.getCommitsTree("b3c138216b0d165a8e8efa2ed4ed31e53b4d9234"), "61ad38f531c7d1819cc0693ae6e78824976d4dfe");

    //     // index.addBlob("file3.txt");
    //     // Commit commit2 = new Commit("b3c138216b0d165a8e8efa2ed4ed31e53b4d9234", "Jake Parker 2", "This is my commmmmit.");
    //     // commit2.commit();
            
    //     // Utils.deleteFile("file1.txt");
    //     // Utils.deleteFile("file2.txt");
    //     // Utils.deleteFile("file3.txt");
    //     // Utils.deleteDirectory("dir");
    // }



    // @Test
    // @DisplayName("Test that creating a commits tree works.")
    // void testCreateTree() throws Exception {
    //     Commit commit = new Commit("Jake Parker", "This is my commit.");
    //     String sTree = commit.constructTreeSha();
    //     assertTrue(Utils.exists("objects/" + sTree));
    // }
    */
}
