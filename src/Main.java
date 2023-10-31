public class Main {
    public static void main(String[] args) {
        System.out.println();
        try {
            deleteAll();
            runFirst2Commits();
            addDeleteFile2ToIndex();
            commitDeletingFile2();
            
            Git git = new Git();
            git.initialize();
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

            deleteAll();
        }
        catch(Exception e) {}
        System.out.println();
    }

    public static void runFirst2Commits() throws Exception {
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
    }

    public static void addDeleteFile2ToIndex() throws Exception {
        Utils.writeInFile("index", "*deleted* file3.txt");
    }

    public static void commitDeletingFile2() throws Exception {
        Git git = new Git();
        git.initialize();
        Commit commit3 = new Commit("1e4433e3846fae0af93eccb7c9819e7ed196add3", "Jake Parker 3", "Anotha one");
        commit3.commit(git);
    }

    public static void deleteAll() throws Exception {
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
}