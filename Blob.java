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

    public static String hashString(String sToBeHashed) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(sToBeHashed.getBytes());
            byte[] b = md.digest();
            StringBuffer buf = new StringBuffer();
            char aHexDigits[] = {
                '0', '1', '2', '3',
                '4', '5', '6', '7',
                '8', '9', 'a', 'b',
                'c', 'd', 'e', 'f'
            };
            for (int i=0; i<16; i++) {
                buf.append(aHexDigits[(b[i] >> 4) & 0x0f]);
                buf.append(aHexDigits[b[i] & 0x0f]);
            }
            return buf.toString();
        } catch (Exception e) {
            throw e;
        }
    }

    public String getHash() {
        return this.sHash;
    }
}
