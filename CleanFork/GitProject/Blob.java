import java.io.File;

public class Blob {
    private String sHash;
    public Blob(String sPath) throws Exception {
        File objects = new File("./objects");
        if (!objects.exists())
            objects.mkdirs();
        String sFile = Utils.readFile(sPath);
        this.sHash = Utils.hashString(sFile);
        Utils.writeFile("./objects/" + this.sHash, sFile);
    }

    public String getHash() {
        return this.sHash;
    }
}