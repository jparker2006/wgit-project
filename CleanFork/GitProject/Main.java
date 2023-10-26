public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try {
            Git git = new Git();
            git.initialize();

            Utils.writeFile("dir/file1.txt", "file1");
            Utils.writeFile("dir/file2.txt", "file2");
            Utils.writeFile("dir/file3.txt", "file3");

            Tree tree = new Tree();
            tree.addDirectory("dir");

        }
        catch (Exception e) {}
        
    }
}
