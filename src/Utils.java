import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class Utils {

    public static void writeFile(String path, String content) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            createFile(path);
        }

        FileWriter writer = new FileWriter(path, false);
        writer.write(content);
        writer.close();
    }

    public static String readFile(String path) throws IOException {
        return Files.readString(Paths.get(path));
    }

    public static boolean exists(String path) {
        return new File(path).exists();
    }

    public static boolean deleteDirectory(String path) throws IOException {
        File directory = new File(path);
        File[] directoryFiles = directory.listFiles();
        if (directoryFiles != null) {
            for (File file : directoryFiles) {
                deleteDirectory(file.getPath());
            }
        }
        return directory.delete();
    }

    // private static boolean contains(String key, String[] list) {
    //     for (String item : list) {
    //         if (key.equals(item)) {
    //             return true;
    //         }
    //     }
    //     return false;
    // }

    public static void deleteFile(String path) throws IOException {
        Files.deleteIfExists(Paths.get(path));
    }

    public static void createFile(String path) throws IOException {
        Path pathObject = Paths.get(path);

        if (pathObject.getParent() != null) {
            Files.createDirectories(pathObject.getParent());
        }

        Files.createFile(pathObject);
    } 

    public static String hashString(String sToBeHashed) throws Exception {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] baHash = mDigest.digest(sToBeHashed.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<baHash.length; i++) {
            sb.append(Integer.toString((baHash[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public static void writeInFile(String path, String content) throws Exception {
        File file = new File(path);
        FileWriter fw = new FileWriter(file, true);
        fw.write(content);
        fw.close();
    }

    public static void createFolder(String path) throws IOException {
        Path pathObject = Paths.get(path);

        if (pathObject.getParent() != null) {
            Files.createDirectories(pathObject.getParent());
        }

        Files.createDirectories(pathObject);
    }

    public static String getFirstWordOfString(String s) {
        return s.substring(0, s.indexOf(" "));
    }

    public static String getLastWordOfString (String s) {
        if (s == null || s .isEmpty())
            return "";
        String[] words = s.trim().split("\\s+");
        return words[words.length - 1];
    }


    public static void removeLineInFile(String path, int line) throws Exception {
        String sFile = readFile(path);
        String[] list = sFile.split("\n");
        String sOutput = "";
        for (int i=0; i<list.length; i++) {
            if (line != i)
                sOutput += list[i] + "\n";
        }
        writeFile(path, sOutput.trim());
    }
}
