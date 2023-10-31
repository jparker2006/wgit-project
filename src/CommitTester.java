import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

public class CommitTester {
    @AfterAll
    static void afterAll() throws Exception {
        Utils.deleteFile("file1.txt");
        Utils.deleteFile("file2.txt");
        Utils.deleteFile("file3.txt");
        Utils.deleteFile("file5.txt");
        Utils.deleteFile("file6.txt");
        Utils.deleteFile("file7.txt");
        Utils.deleteFile("file8.txt");
        Utils.deleteFile("file9.txt");
        Utils.deleteDirectory("dir");
        Utils.deleteDirectory("dir2");
        Utils.deleteDirectory("objects");
        Utils.deleteFile("index");  
    }

    @Test
    void test1Commit() throws Exception {
        Git git = new Git();
        git.initialize();
        Utils.writeFile("file1.txt", "A");
        Utils.writeFile("file2.txt", "B");
        Utils.writeFile("dir/file4.txt", "D");
        git.addBlob("file1.txt");
        git.addBlob("file2.txt");
        git.addDirectory("dir");
        Commit commit = new Commit("Jake Parker", "This is my commit.");
        commit.commit(git);
        String[] lines = Utils.readFile("objects/5bd6f2a6c57af2b21e928c268b27a453613a4354").split("\n");
        assertEquals("61ad38f531c7d1819cc0693ae6e78824976d4dfe", lines[0]);
        assertEquals("", lines[1]);
        assertEquals("", lines[2]);
    }

    @Test
    void test2Commit() throws Exception {
        Git git = new Git();
        git.initialize();

        Utils.writeFile("file1.txt", "A");
        Utils.writeFile("file2.txt", "B");
        Utils.writeFile("dir/file4.txt", "D");

        git.addBlob("file1.txt");
        git.addBlob("file2.txt");
        git.addDirectory("dir");
        Commit commit = new Commit("Jake Parker", "This is my commit.");
        commit.commit(git);

        Utils.writeFile("file3.txt", "E");
        Utils.writeFile("file5.txt", "B");
        git.addBlob("file3.txt");
        git.addBlob("file5.txt");
        Commit commit2 = new Commit("5bd6f2a6c57af2b21e928c268b27a453613a4354", "Jake Parker", "This is my commit.");
        commit2.commit(git);

        String[] linesC1 = Utils.readFile("objects/5bd6f2a6c57af2b21e928c268b27a453613a4354").split("\n");
        assertEquals("61ad38f531c7d1819cc0693ae6e78824976d4dfe", linesC1[0]);
        assertEquals("", linesC1[1]);
        assertEquals("248ffbd33b174e37f3db5760c269d21c30a05fb4", linesC1[2]);
        String[] linesC2 = Utils.readFile("objects/248ffbd33b174e37f3db5760c269d21c30a05fb4").split("\n");
        assertEquals("867f07e292ba177c727bb5879058eec8ce451e52", linesC2[0]);
        assertEquals("5bd6f2a6c57af2b21e928c268b27a453613a4354", linesC2[1]);
        assertEquals("", linesC2[2]);
        assertEquals(
            Utils.readFile("objects/867f07e292ba177c727bb5879058eec8ce451e52"), 
            "blob : e0184adedf913b076626646d3f52c3b49c39ad6d : file3.txt" + "\n" +
            "blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : file5.txt"
        ); 
    }

    @Test
    void test4Commit() throws Exception {
        Git git = new Git();
        git.initialize();

        Utils.writeFile("file1.txt", "A");
        Utils.writeFile("file2.txt", "B");
        Utils.writeFile("dir/file4.txt", "D");

        git.addBlob("file1.txt");
        git.addBlob("file2.txt");
        git.addDirectory("dir");
        Commit commit = new Commit("Jake Parker", "This is my commit.");
        commit.commit(git);

        Utils.writeFile("file3.txt", "E");
        Utils.writeFile("file5.txt", "B");
        git.addBlob("file3.txt");
        git.addBlob("file5.txt");
        Commit commit2 = new Commit("5bd6f2a6c57af2b21e928c268b27a453613a4354", "Jake Parker", "This is my commit.");
        commit2.commit(git);

        Utils.writeFile("file6.txt", "Egd");
        Utils.writeFile("dir2/file7.txt", "Beglkn");
        git.addBlob("file3.txt");
        git.addDirectory("dir2");
        Commit commit3 = new Commit("248ffbd33b174e37f3db5760c269d21c30a05fb4", "Jake Parker", "This is my committt.");
        commit3.commit(git);

        Utils.writeFile("file8.txt", "owfn");
        Utils.writeFile("file9.txt", "Last file");
        git.addBlob("file8.txt");
        git.addBlob("file9.txt");
        Commit commit4 = new Commit("fd1c491e60d8efad5deef1813e5f5029858b3a87", "Jake Parker", "This is my last committt.");
        commit4.commit(git);

        String[] linesC1 = Utils.readFile("objects/5bd6f2a6c57af2b21e928c268b27a453613a4354").split("\n");
        assertEquals("61ad38f531c7d1819cc0693ae6e78824976d4dfe", linesC1[0]);
        assertEquals("", linesC1[1]);
        assertEquals("248ffbd33b174e37f3db5760c269d21c30a05fb4", linesC1[2]);

        String[] linesC2 = Utils.readFile("objects/248ffbd33b174e37f3db5760c269d21c30a05fb4").split("\n");
        assertEquals("867f07e292ba177c727bb5879058eec8ce451e52", linesC2[0]);
        assertEquals("5bd6f2a6c57af2b21e928c268b27a453613a4354", linesC2[1]);
        assertEquals("fd1c491e60d8efad5deef1813e5f5029858b3a87", linesC2[2]);

        String[] linesC3 = Utils.readFile("objects/fd1c491e60d8efad5deef1813e5f5029858b3a87").split("\n");
        assertEquals("6c9cb19758a9951174d61afde3d047e6c107db4f", linesC3[0]);
        assertEquals("248ffbd33b174e37f3db5760c269d21c30a05fb4", linesC3[1]);
        assertEquals("c1b51cb62a76e74f88b97de531faca6ce43182c1", linesC3[2]);

        String[] linesC4 = Utils.readFile("objects/c1b51cb62a76e74f88b97de531faca6ce43182c1").split("\n");
        assertEquals("c24c890af8cb20fad112d83855753090ba86cd1f", linesC4[0]);
        assertEquals("fd1c491e60d8efad5deef1813e5f5029858b3a87", linesC4[1]);
        assertEquals("", linesC4[2]);

        assertEquals(
            Utils.readFile("objects/61ad38f531c7d1819cc0693ae6e78824976d4dfe"), 
            "blob : 6dcd4ce23d88e2ee9568ba546c007c63d9131c1b : file1.txt" + "\n" +
            "blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : file2.txt" + "\n" +
            "tree : b0273d519d774118b52b3c4ac1990328189ecdf8 : dir"
        ); 

        assertEquals(
            Utils.readFile("objects/867f07e292ba177c727bb5879058eec8ce451e52"), 
            "blob : e0184adedf913b076626646d3f52c3b49c39ad6d : file3.txt" + "\n" +
            "blob : ae4f281df5a5d0ff3cad6371f76d5c29b6d953ec : file5.txt"
        ); 

        assertEquals(
            Utils.readFile("objects/6c9cb19758a9951174d61afde3d047e6c107db4f"), 
            "blob : e0184adedf913b076626646d3f52c3b49c39ad6d : file3.txt" + "\n" +
            "tree : 90483d58e72a9bb1c6b34aa7918c5ef790d4b2ba : dir2"
        ); 

        assertEquals(
            Utils.readFile("objects/c24c890af8cb20fad112d83855753090ba86cd1f"), 
            "blob : 7b9f6336ef257a6a69cede83c664c6b092e076ff : file8.txt" + "\n" +
            "blob : dae441a494e21a12eab608431284a9cc61f1c879 : file9.txt"
        );
    }
   
    @Test
    void testDeleteCommit() throws Exception {
        Git git = new Git();
        git.initialize();

        Utils.writeFile("file1.txt", "A");
        Utils.writeFile("file2.txt", "B");
        Utils.writeFile("file3.txt", "C");
        Utils.writeFile("dir/file4.txt", "D");

        Utils.writeFile("file5.txt", "E");
        Utils.writeFile("file6.txt", "F");
        Utils.writeFile("file7.txt", "G");
        Utils.writeFile("file8.txt", "H");

        git.addBlob("file1.txt");
        git.addBlob("file2.txt");
        git.addDirectory("dir");
        Commit commit = new Commit("Jake Parker", "This is my commit.");
        commit.commit(git);

        git.addBlob("file3.txt");
        Commit commit2 = new Commit("5bd6f2a6c57af2b21e928c268b27a453613a4354", "Jake Parker 2", "This is my commmmmit.");
        commit2.commit(git);

        Utils.writeInFile("index", "*deleted* file3.txt");

        Commit commit3 = new Commit("1e4433e3846fae0af93eccb7c9819e7ed196add3", "Jake Parker 3", "Anotha one");
        commit3.commit(git);

        git.addBlob("file5.txt");
        git.addBlob("file6.txt");
        Commit commit4 = new Commit("9f0ecf0ae1ed93505ec9fa4d878641b9f1318b73", "Jake Parker 4", "Committttt");
        commit4.commit(git);

        git.addBlob("file7.txt");
        Commit commit5 = new Commit("87ef393f0326a9938b01c237326fdddc7c0f48fc", "JP5", "Cummit");
        commit5.commit(git);

        git.deleteFile("file5.txt");
        git.addBlob("file8.txt");
        Commit commit6 = new Commit("082bd185e7002ae299f54181bcbe5a1751d59eb3", "JP6", "Last 1");
        commit6.commit(git);

        String sCommit6TreeHash = commit6.getCommitsTree(Utils.readFile("objects/082bd185e7002ae299f54181bcbe5a1751d59eb3").split("\n")[2]);
        assertTrue(Utils.readFile("objects/" + sCommit6TreeHash).split("\n")[0].equals("*deleted* file5.txt"));
    }
}

/*
OLD COMMIT TESTER

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