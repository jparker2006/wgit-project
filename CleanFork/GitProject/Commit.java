import java.io.FileWriter;
import java.text.SimpleDateFormat;  
import java.util.Date;

public class Commit {
    private String sTreeSha, sParentSha, sChildSha, sAuthor, sDate, sSummary, sCommitSha;
    public Commit(String sParentSha, String sAuthor, String sSummary) throws Exception {
        this.sTreeSha = constructTreeSha();
        this.sParentSha = sParentSha;
        this.sChildSha = "";
        this.sAuthor = sAuthor;
        this.sDate = getDate();
        this.sSummary = sSummary;
    }

    public Commit(String sAuthor, String sSummary) throws Exception {
        this("", sAuthor, sSummary);
    }

    public void commit() throws Exception {
        StringBuilder sCommit = new StringBuilder(
            this.sTreeSha   + "\n" +
            this.sParentSha + "\n" +
            this.sAuthor    + "\n" +
            this.sDate      + "\n" +
            this.sSummary
        );

        this.sCommitSha = Utils.hashString(sCommit.toString());

        sCommit.insert(sCommit.indexOf("\n", sCommit.indexOf("\n") + 1), this.sChildSha + "\n");
        Utils.writeFile("objects/" + this.sCommitSha, sCommit.toString());
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();  
        return sdf.format(date);  
    }

    public String constructTreeSha() throws Exception {
        Tree tree = new Tree();
        return Utils.hashString(tree.stringify());
    }

    public String getCommitSha() {
        return this.sCommitSha;
    }
}