import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Blob {
    private String sHash;
    public Blob(String sFilePath) throws Exception {
        StringBuilder sBuilder = new StringBuilder();
        BufferedReader buffer = new BufferedReader(new FileReader(sFilePath));
        String sFile = "";
        while (null != (sFile = buffer.readLine())) {
            sBuilder.append(sFile).append("\n");
        }
        buffer.close();
        this.sHash = hashString(sBuilder.toString());
        File file = new File("./objects/" + sHash);
        file.createNewFile();
    }

    public String hashString(String sToBeHashed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(sToBeHashed.getBytes());
            BigInteger nBigInt = new BigInteger(1, messageDigest);
            String sHash = nBigInt.toString(16);
            while (sHash.length() < 32) {
                sHash = "0" + sHash;
            }
            return sHash;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getHash() {
        return this.sHash;
    }
}