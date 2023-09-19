import java.io.IOException;

public class GitTester{
    public static void main (String [] args) throws Exception
    {
        Git git = new Git();
        git.initialize();

        git.addBlob("a.txt");
        git.addBlob("b.txt");
        // git.add("c.txt");

        // git.deleteBlob ("a.txt");
        // git.remove("b.txt");
        // git.remove("c.txt");
        git.addBlob("c.txt");
    }
}