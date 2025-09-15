import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class main {

    public static List<String> readAllLines(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    public static void main(String[] args) {
        try {
            List<String> lines = readAllLines("input.txt");
            for (String line : lines) {
                try {
                    int binaryCode = InputParser.parseLine(line);
                    if (binaryCode != -1) {
                        System.out.printf(Integer.toBinaryString(binaryCode));
                    }
                    // something
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + line);
                    System.err.println("  " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}