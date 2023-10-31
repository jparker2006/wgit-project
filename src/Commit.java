import java.text.SimpleDateFormat;
import java.util.Date;

public class Commit {
    private String sTreeSha, sParentSha, sChildSha, sAuthor, sDate, sSummary, sCommitSha;
    public Commit(String sParentSha, String sAuthor, String sSummary) throws Exception {
        this.sParentSha = sParentSha;
        this.sChildSha = "";
        this.sAuthor = sAuthor;
        this.sDate = getDate();
        this.sSummary = sSummary;
        this.sTreeSha = constructTreeSha();
    }

    public Commit(String sAuthor, String sSummary) throws Exception {
        this("", sAuthor, sSummary);
    }

    public void commit(Git git) throws Exception {
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

        if (!this.sParentSha.isEmpty())
            updatePreviousCommit(this.sParentSha);

        git.clearIndex();
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
        Date date = new Date();  
        return sdf.format(date);  
    }

    public String constructTreeSha() throws Exception {
        Tree tree = new Tree();
        String sIndex = Utils.readFile("index");
        String[] aEntries = sIndex.split("\n");
        for (int i=0; i<aEntries.length; i++) {
            String sType = Utils.getFirstWordOfString(aEntries[i]);
            if (sType.equals("blob"))
                tree.add(aEntries[i]);
            else if (sType.equals("tree"))
                tree.addDirectory(Utils.getLastWordOfString(aEntries[i]));
            else if (sType.equals("*deleted*"))
                tree.delete(aEntries[i].split(" ")[1], this.sParentSha);
            else if (sType.equals("*edited*"))
                tree.edit(aEntries[i].split(" ")[1], this.sParentSha);
        }
        tree.toFile();
        return Utils.hashString(tree.stringify());
    }

    public String getCommitSha() {
        return this.sCommitSha;
    }

    public String getCommitsTree(String sCommitsHash) throws Exception {
        String sCommit = Utils.readFile("objects/" + sCommitsHash);
        return sCommit.substring(0, sCommit.indexOf("\n"));
    }

    public void updatePreviousCommit(String sParentHash) throws Exception {
        String sFile = Utils.readFile("objects/" + sParentHash);
        String[] aSplitFile = sFile.split("\n");
        aSplitFile[2] = this.sCommitSha;
        sFile = "";
        for (int i=0; i<aSplitFile.length; i++) {
            sFile += aSplitFile[i] + "\n";
        }
        sFile = sFile.trim();
        Utils.writeFile("objects/" + sParentHash, sFile);
    }
}