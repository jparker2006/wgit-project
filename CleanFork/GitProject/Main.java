public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try {
            Git index = new Git();
            Utils.writeFile("Test.txt", "This is file contents.");
            index.addBlob("Test.txt");

        }
        catch (Exception e) {}
        
    }
}
