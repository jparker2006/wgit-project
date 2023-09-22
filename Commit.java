import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Commit
{
    private String summary;
    private String author;
    private String optionalParent;
    private String shaOfTree;
    public Commit(String summary, String author, String optionalParent) throws IOException
    {
        createTree();
        File f = new File("objects/" + "Commit");
        FileWriter fw = new FileWriter(f);
        this.summary = summary;
        this.author = author;
        this.optionalParent = optionalParent;
        fw.write(shaOfTree + "\n");
        if (optionalParent.equals(""))
        {
            fw.write("\n");
        }
        else if (!optionalParent.equals(""))
        {
            fw.write(optionalParent + "\n");
        }
        fw.write("\n");//This is for nextCommit
        fw.write(author + "\n");
        fw.write(getDate() + "\n");
        fw.write(summary);
        fw.close();
    }
    public void createTree() throws IOException
    {
        tree tree = new tree();
        tree.toFile();
        shaOfTree = tree.returnHash();
    }
    public Calendar getDate()
    {
        Calendar now = GregorianCalendar.getInstance();
        return now;
    }
    public static String hashString(String sToBeHashed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(sToBeHashed.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public String genSha1() throws IOException
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader("objects/" + "Commit"));
        while (br.ready())
        {
            sb.append(br.readLine());
        }
        br.close();
        return hashString(sb.toString());
    }
    public String getShaOfTree() {
        return shaOfTree;
    }
}