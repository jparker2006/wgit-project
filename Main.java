public class Main {
    public static void main(String[] args) throws Exception {
        Git index = new Git();
        index.initialize();
        index.addBlob("/Users/jparker/Desktop/wgit-project/test.txt");
    }
}
